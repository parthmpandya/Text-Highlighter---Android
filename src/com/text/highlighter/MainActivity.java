package com.text.highlighter;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText etText;
	ArrayList<SelectedWordPOJO> selectedWordsCounter = new ArrayList<SelectedWordPOJO>();
	Button btnGetHighlightedWords;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etText = (EditText) findViewById(R.id.note);
		etText.setCustomSelectionActionModeCallback(new StyleCallBack());
		etText.setVerticalScrollBarEnabled(true);
		etText.requestFocus();
		
		btnGetHighlightedWords = (Button) findViewById(R.id.btnGetHighlightedWords);
		
		btnGetHighlightedWords.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				StringBuffer sb = new StringBuffer();
				for(int i = 0 ; i< selectedWordsCounter.size() ; i++){
					sb.append(selectedWordsCounter.get(i).word+",\n");
				}
				etText.setText("Highlighted Words:\n\n"+sb.toString());
			}
		});
		
	}
	
	 class StyleCallBack implements ActionMode.Callback {

			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				MenuInflater inflater = mode.getMenuInflater();
				inflater.inflate(R.menu.style, menu);
				menu.removeItem(android.R.id.selectAll);
				menu.removeItem(android.R.id.copy);
				menu.removeItem(android.R.id.cut);
				return true;
			}

			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}

			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				int start = etText.getSelectionStart();
				int end = etText.getSelectionEnd();
				
				SpannableStringBuilder ssb = new SpannableStringBuilder(etText.getText());
				final ForegroundColorSpan foreGroundWhite = new ForegroundColorSpan(Color.WHITE); 
				final BackgroundColorSpan backgroundRed = new BackgroundColorSpan(Color.RED);
			//	final CharacterStyle csBold = new StyleSpan(Typeface.BOLD);

				final ForegroundColorSpan foreGroundBlack = new ForegroundColorSpan(Color.BLACK); 
				final BackgroundColorSpan backgroundWhite = new BackgroundColorSpan(Color.WHITE);
			//	final CharacterStyle csNormal = new StyleSpan(Typeface.NORMAL);
				
				switch (item.getItemId()) {
				case R.id.more:
					
					final CharSequence selectedText = etText.getText().subSequence(start, end);
					boolean b = checkWordStorage(selectedText.toString(), start, end);
					if(b){
						// DESELECT
				        ssb.setSpan(backgroundWhite, start, end, 1);
				//	    ssb.setSpan(csNormal, start, end, 1);
			            ssb.setSpan(foreGroundBlack, start, end, 1);
			            Log.v("Start:"+start,"End:"+end);
			            etText.setText(ssb);

					}else{
						//SELECT
				    ssb.setSpan(backgroundRed, start, end, 1);
				  //  ssb.setSpan(csBold, start, end, 1);
		            ssb.setSpan(foreGroundWhite, start, end, 1);
		            Log.v("Start:"+start,"End:"+end);
		            etText.setText(ssb);
					}
		            return true;
				}
				return false;
			}

			public void onDestroyActionMode(ActionMode mode) {
			}
		}

	 private boolean checkWordStorage(String word, int st, int end){
		 boolean result = false;
		 for(int i = 0 ; i < selectedWordsCounter.size() ; i++){
			 int eachStart = selectedWordsCounter.get(i).getStart();
			 int eachEnd = selectedWordsCounter.get(i).getEnd();
			 String eachWord = selectedWordsCounter.get(i).getWord();
			 
			 if(word.equals(eachWord) && st == eachStart && end == eachEnd){
				 // word is already exist and need to DESELECT
				 selectedWordsCounter.remove(i);
				 result = true;
				 break;
			 }
		 }
		 
		 if(!result){
			 // word is not found in array, We need to add it in array	 
			 selectedWordsCounter.add(new SelectedWordPOJO(word, st, end));
		 }
		 
		 return result;
	 }
	 
}

