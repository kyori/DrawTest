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

	private ImageView m2ndImageView, m3rdImageView;						//�̹��� ��
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		m2ndImageView = (ImageView)findViewById(R.id.gray_imageview);		//��� �̹�����. �̹��� �並
		m3rdImageView = (ImageView)findViewById(R.id.gray_drawable);		
		
		findViewById(R.id.convert_imageview).setOnClickListener(this);
		findViewById(R.id.convert_drawable).setOnClickListener(this);
		findViewById(R.id.clear).setOnClickListener(this);
	}
	
	//�̹����並 grayscale�� ����
	public void setGrayScale(ImageView v){
		
		ColorMatrix matrix = new ColorMatrix(new float[]{
				2, -1, 0, 0,0,
				-1,1,0,0,0,
				1,-1,3,0,0,
				0,0,0,1,0});
		//����
//		ColorMatrix matrix = new ColorMatrix(new float[]{
//				-1, 0, 0, 0, 255,
//				0, -1, 0, 0, 255,
//				0, 0, -1, 0, 255,
//				0, 0, 0, 1, 0});		
		//matrix.setSaturation(4);						//0�̸� grayscale
		ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
		v.setColorFilter(cf);
	}
	
	//drawable�� grayscale�� ����
	public Drawable convertGrayScale(Drawable d){
		//brightness
		ColorMatrix matrix = new ColorMatrix(new float[]{
				1, 0, 0, 0, 40,
				0, 1, 0, 0, 40,
				0, 0, 1, 0, 40,
				0, 0, 0, 1, 0
		});
	    //matrix.setSaturation(0);					//0�̸� grayscale
	    ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
	    d.setColorFilter(cf);

	    return d;
	}

	@Override
	public void onClick(View v) {
		int view = v.getId();
		if(view == R.id.convert_imageview){
			setGrayScale(m2ndImageView);									//�ش� �̹��� �丸 grayscale�� ����
		}
		else if(view == R.id.convert_drawable){
			// mutate�� �� ������ ���� 3���� �̹����䰡 ���� ���ҽ� �̹����� ����Ѵ�.
			//�׷��� �׳� getDrawable�� drawable�� ���� �����ϸ�
			//�ش� ���ҽ��� ����Ǵ� ���̹Ƿ� �� ���ҽ��� ���� �ٸ� ���� �̹����� ���� ����ȴ�.
			//�̷� ��츦 ���� ���� mutate�� �Ἥ drawable�� �������� ���̴�.
			Drawable d = m3rdImageView.getDrawable().mutate();		//drawable�� ����.
			m3rdImageView.setImageDrawable(convertGrayScale(d));		//grayscale�� ���� �� �̹��� ����
			m3rdImageView.invalidate();										//�̹��� �� ���� ��ħ
			
			getResources().getDrawable(R.drawable.sample);
		}
		else if(view == R.id.clear){
			m2ndImageView.clearColorFilter();						//�̹������� �÷����� ����
			m3rdImageView.getDrawable().clearColorFilter();		//drawable�� �÷����� ����
			m3rdImageView.invalidate();								//3��° �̹����� ���� ��ħ
		}
	}
}