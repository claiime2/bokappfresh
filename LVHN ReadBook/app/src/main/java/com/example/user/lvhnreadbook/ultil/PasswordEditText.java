package com.example.user.lvhnreadbook.ultil;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.user.lvhnreadbook.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("AppCompatCustomView")
public class PasswordEditText extends EditText{
    Drawable eye,eyeStrike;
    Boolean visible=false;
    private boolean useStrike=false;
    private boolean useValidate=false;
    Drawable drawable;
    String MATCHER_PATTERN= "((?=.*\\d)(?=.*[A_Z])(?=.*[a-z]).{6,20})";
    Pattern pattern;
    Matcher matcher;

    public PasswordEditText(Context context) {
        super(context);
        khoitao(null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        khoitao(attrs);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        khoitao(attrs);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        khoitao(attrs);
    }
    private void khoitao(AttributeSet attrs){
        this.pattern=Pattern.compile(MATCHER_PATTERN);
        if(attrs!=null){
            TypedArray array=getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PassWordEditText,0,0);
            this.useStrike=array.getBoolean(R.styleable.PassWordEditText_useStrike,false);
            this.useValidate=array.getBoolean(R.styleable.PassWordEditText_useValidate,false);
        }
        eye= ContextCompat.getDrawable(getContext(),R.drawable.ic_visibility_black_24dp).mutate();
        eyeStrike=ContextCompat.getDrawable(getContext(),R.drawable.ic_visibility_off_black_24dp).mutate();
        if(this.useValidate){
            setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        String chuoi = getText().toString();
                        TextInputLayout textInputLayout= (TextInputLayout) v.getParentForAccessibility();
                        matcher=pattern.matcher(chuoi);
                        if(!matcher.matches()){
                            textInputLayout.setErrorEnabled(true);
                            textInputLayout.setError("Mật khẩu ít nhất 6 kí tự và có in hoa");
                        }else{
                            textInputLayout.setErrorEnabled(false);
                            textInputLayout.setError("");
                        }

                    }
                }
            });
        }
        caidat();
    }
    private void caidat(){
        setInputType(InputType.TYPE_CLASS_TEXT|(visible? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD:InputType.TYPE_TEXT_VARIATION_PASSWORD));
        Drawable[] drawables=getCompoundDrawables();
        drawable= useStrike&&!visible? eyeStrike:eye;
        setCompoundDrawablesWithIntrinsicBounds(drawables[0],drawables[1],drawable,drawables[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_UP && event.getX()>=(getRight() - drawable.getBounds().width())){
            visible=!visible;
            caidat();
            invalidate();
        }

            return super.onTouchEvent(event);

    }
}
