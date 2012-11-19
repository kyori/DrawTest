package com.example.drawtest;

import com.example.drawtest.R;

import android.app.Activity;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Grayscale extends Activity implements OnClickListener {

	private ImageView m2ndImageView, m3rdImageView;						//이미지 뷰
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		m2ndImageView = (ImageView)findViewById(R.id.gray_imageview);		//가운데 이미지뷰. 이미지 뷰를
		m3rdImageView = (ImageView)findViewById(R.id.gray_drawable);		
		
		findViewById(R.id.convert_imageview).setOnClickListener(this);
		findViewById(R.id.convert_drawable).setOnClickListener(this);
		findViewById(R.id.clear).setOnClickListener(this);
	}
	
	//이미지뷰를 grayscale로 변경
	public void setGrayScale(ImageView v){
		
		ColorMatrix matrix = new ColorMatrix(new float[]{
				2, -1, 0, 0,0,
				-1,1,0,0,0,
				1,-1,3,0,0,
				0,0,0,1,0});
		//반전
//		ColorMatrix matrix = new ColorMatrix(new float[]{
//				-1, 0, 0, 0, 255,
//				0, -1, 0, 0, 255,
//				0, 0, -1, 0, 255,
//				0, 0, 0, 1, 0});		
		//matrix.setSaturation(4);						//0이면 grayscale
		ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
		v.setColorFilter(cf);
	}
	
	//drawable만 grayscale로 변경
	public Drawable convertGrayScale(Drawable d){
		//brightness
		ColorMatrix matrix = new ColorMatrix(new float[]{
				1, 0, 0, 0, 40,
				0, 1, 0, 0, 40,
				0, 0, 1, 0, 40,
				0, 0, 0, 1, 0
		});
	    //matrix.setSaturation(0);					//0이면 grayscale
	    ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
	    d.setColorFilter(cf);

	    return d;
	}

	@Override
	public void onClick(View v) {
		int view = v.getId();
		if(view == R.id.convert_imageview){
			setGrayScale(m2ndImageView);									//해당 이미지 뷰만 grayscale로 변경
		}
		else if(view == R.id.convert_drawable){
			// mutate를 한 이유는 현재 3개의 이미지뷰가 같은 리소스 이미지를 사용한다.
			//그런데 그냥 getDrawable로 drawable을 얻어와 변경하면
			//해당 리소스가 변경되는 것이므로 그 리소스를 쓰는 다른 뷰의 이미지도 같이 변경된다.
			//이런 경우를 막기 위해 mutate를 써서 drawable을 가져오는 것이다.
			Drawable d = m3rdImageView.getDrawable().mutate();		//drawable을 얻어옴.
			m3rdImageView.setImageDrawable(convertGrayScale(d));		//grayscale로 변경 후 이미지 지정
			m3rdImageView.invalidate();										//이미지 뷰 새로 고침
			
			getResources().getDrawable(R.drawable.sample);
		}
		else if(view == R.id.clear){
			m2ndImageView.clearColorFilter();						//이미지뷰의 컬러필터 삭제
			m3rdImageView.getDrawable().clearColorFilter();		//drawable의 컬러필터 삭제
			m3rdImageView.invalidate();								//3번째 이미지뷰 새로 고침
		}
	}
}