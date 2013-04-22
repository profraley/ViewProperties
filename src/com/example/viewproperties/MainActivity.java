package com.example.viewproperties;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity implements OnCheckedChangeListener, OnSeekBarChangeListener, OnClickListener {
	RadioGroup mPropertyGroup;
	SeekBar mSlider;
	View mView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPropertyGroup = (RadioGroup) findViewById(R.id.propertyGroup);
		mSlider = (SeekBar) findViewById(R.id.slider);
		mView =  (View) findViewById(R.id.view);
		
		mView.setOnClickListener(this);
		mSlider.setOnSeekBarChangeListener(this);
		mPropertyGroup.setOnCheckedChangeListener(this);
		mPropertyGroup.check(R.id.alpha);
		mSlider.setProgress(100);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		
		if (item.getItemId() == R.id.reset) {
			mView.setAlpha(1f);
			mView.setScaleX(1f);
			mView.setScaleY(1f);
			mView.setTranslationX(0);
			mView.setTranslationY(0);
			mView.setRotation(0);
			int oldId = mPropertyGroup.getCheckedRadioButtonId();
			mPropertyGroup.clearCheck();
			mPropertyGroup.check(oldId);
		} else if (item.getItemId() == R.id.animateAll) {
			final float alpha = mView.getAlpha();
			final float transX = mView.getTranslationX();
			final float transY = mView.getTranslationY();
			final float scale = mView.getScaleX();
			mView.animate().rotationBy(360).alpha(0).scaleX(0).scaleY(1).translationXBy(100).translationYBy(-100).setDuration(2000).setListener(new AnimatorListener() {

				@Override
				public void onAnimationCancel(Animator animation) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onAnimationEnd(Animator animation) {
					
					mView.setAlpha(alpha);
					mView.setScaleX(scale);
					mView.setScaleY(scale);
					mView.setTranslationX(transX);
					mView.setTranslationY(transY);
				}

				@Override
				public void onAnimationRepeat(Animator animation) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onAnimationStart(Animator animation) {
					// TODO Auto-generated method stub
					
				}
				
			}).start();
		}
		return true;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int progress;
		switch (checkedId) {
			case R.id.alpha :
				mSlider.setProgress((int)(mView.getAlpha()*100));
				break;
			case R.id.scale :
				final float currentScale = mView.getScaleX();
				progress = (int) (currentScale * 100);
				progress -= 50;
				mSlider.setProgress(progress);
				break;
			case R.id.transX :
				final float currentTransX = mView.getTranslationX();
				progress = (int)(currentTransX + 50);
				mSlider.setProgress(progress);				
				break;
			case R.id.transY :
				final float currentTransY = mView.getTranslationY();
				progress = (int)(currentTransY + 50);
				mSlider.setProgress(progress);	
				break;
			case R.id.rotateZ : 
				final float currentRotation = mView.getRotation();
				float rotateProgress = currentRotation / 45;
				progress = (int)(rotateProgress * 50);
				progress += 50;
				mSlider.setProgress(progress);
				break;
		}
		
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		int checkedId = mPropertyGroup.getCheckedRadioButtonId();
		
		switch (checkedId) {
			case R.id.alpha :
				float alpha = (float) progress / 100f; //need to convert to float for float division else integer math will remove to whole number
				mView.setAlpha(alpha); //setAlpha() takes float from 0-1
				break;
			case R.id.scale :
				//Scale from .5 to 1.5. 
				//Views start with a scale value of 1. 1.5 is one and a half times bigger than normal. .5 is half as small as normal.
				float scaleProgress = (float) progress / 100f;
				scaleProgress += .5f;
				float scale = scaleProgress;
				mView.setScaleX(scale);
				mView.setScaleY(scale);
				break;
			case R.id.transX :
				progress -= 50;
				mView.setTranslationX(progress);
				break;
			case R.id.transY :
				progress -= 50;
				mView.setTranslationY(progress);
				break;
			case R.id.rotateZ : 
				float rotateProgress = (float) progress / 100f;
				float rotate = -45;
				rotate += 90 * rotateProgress;
				mView.setRotation(rotate);
				break;
		}
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View view) {
		if (view == mView) {
			int checkedId = mPropertyGroup.getCheckedRadioButtonId();
			final View parent = (View)mView.getParent();
			final int pHeight = parent.getHeight() - (parent.getPaddingTop() + parent.getPaddingBottom());
			final int pWidth = parent.getWidth() - (parent.getPaddingLeft() + parent.getPaddingRight());
			
			switch (checkedId) {
				case R.id.alpha :
					final float alpha = mView.getAlpha();
					float toAlpha = 1f;
					if (alpha < .5f) {
						toAlpha = 1f - alpha;
					} else {
						toAlpha = -alpha;
					}
					mView.animate().alpha(toAlpha).setDuration(1000).setListener(new AnimatorListener() {

						@Override
						public void onAnimationCancel(Animator animation) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onAnimationEnd(Animator animation) {
							mView.setAlpha(alpha);
						}

						@Override
						public void onAnimationRepeat(Animator animation) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onAnimationStart(Animator animation) {
							// TODO Auto-generated method stub
							
						}
						
					});
					break;
				case R.id.scale :
					final float scale = mView.getScaleX();
					float toScale = 0f;
					if (scale < 1) {
						toScale = 1.5f - scale;
					} else {
						toScale = -scale;
					}
					mView.animate().scaleX(toScale).scaleY(toScale).setDuration(1000).setListener(new AnimatorListener() {

						@Override
						public void onAnimationCancel(Animator animation) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onAnimationEnd(Animator animation) {
							mView.setScaleX(scale);
							mView.setScaleY(scale);
						}

						@Override
						public void onAnimationRepeat(Animator animation) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onAnimationStart(Animator animation) {
							// TODO Auto-generated method stub
							
						}
						
					});
					break;
				case R.id.transX :
					final float transX = mView.getTranslationX();
					float toX = 0f;
					if (transX < 0) {
						toX = (transX * -1) + (pWidth / 2);
						toX -= (mView.getWidth() / 2);
					} else {
						toX -= (transX) + (pWidth / 2);
						toX += (mView.getWidth() / 2);
					}
					mView.animate().translationX(toX).setDuration(1000).setListener(new AnimatorListener() {

						@Override
						public void onAnimationCancel(Animator animation) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onAnimationEnd(Animator animation) {
							mView.setTranslationX(transX);
						}

						@Override
						public void onAnimationRepeat(Animator animation) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onAnimationStart(Animator animation) {
							// TODO Auto-generated method stub
							
						}
						
					});
					break;
				case R.id.transY :					
					final float transY = mView.getTranslationY();
					float toY = 0f;
					if (transY < 0) {
						toY = (transY * -1) + (pHeight / 2);
						toY -= (mView.getHeight() / 2);
					} else {
						toY -= (transY) + (pHeight / 2);
						toY += (mView.getHeight() / 2);
					}
					
					mView.animate().translationY(toY).setDuration(1000).setListener(new AnimatorListener() {

						@Override
						public void onAnimationCancel(Animator animation) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onAnimationEnd(Animator animation) {
							mView.setTranslationY(transY);
						}

						@Override
						public void onAnimationRepeat(Animator animation) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onAnimationStart(Animator animation) {
							// TODO Auto-generated method stub
							
						}
						
					});
					break;
				case R.id.rotateZ : 
					mView.animate().rotationBy(360).setDuration(1000).start();
					break;
			}
		}
		
	}

}
