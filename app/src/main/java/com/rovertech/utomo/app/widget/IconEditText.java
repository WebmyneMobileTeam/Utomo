package com.rovertech.utomo.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rovertech.utomo.app.R;

import java.util.regex.Pattern;


public class IconEditText extends LinearLayout {

    private static final String TAG = IconEditText.class.getSimpleName();

    /**
     * UI Constants
     */
    private static final float ICON_WEIGHT = 0.15f;
    private static final float EDIT_TEXT_WEIGHT = 0.85f;

    private static final String HINT_PREFIX = " ";

    /**
     * Resource pointer for the Icon to display.
     */
    private Integer _iconResource;

    /**
     * The Hint text to display.
     */
    private String _hint;
    private int mMaxLenth = 100;

    /**
     * Indicates if the EditText is for a password.
     */

    private int _inputType = 0;

    /**
     * UI Components
     */
    private ImageView _icon;
    private EditText _editText;

    /**
     * IconTextEdit Constructor
     *
     * @param context
     */
    public IconEditText(Context context) {
        this(context, null);
    }

    /**
     * IconTextEdit Constructor
     *
     * @param context
     * @param attrs
     */
    public IconEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * IconTextEdit Constructor
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public IconEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.parseAttributes(context, attrs);
        this.initialize();
    }

    /**
     * Parses out the custom attributes.
     *
     * @param context
     * @param attrs
     */
    private void parseAttributes(Context context, AttributeSet attrs) {
        Log.d(TAG, "parseAttributes()");
        if (attrs == null) {
            return;
        }

        TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.IconEditText, 0, 0);

        try {
            _iconResource = a.getResourceId(R.styleable.IconEditText_iconSrc, 0);
            _hint = a.getString(R.styleable.IconEditText_hint);
            _inputType = a.getInt(R.styleable.IconEditText_inputType, 0);
            mMaxLenth = a.getInt(R.styleable.IconEditText_maxLength, 0);

            Log.d(TAG, "{ _iconResource: " + _iconResource + ", _hint: " + _hint + ", _inputType:" + _inputType + "}");
        } catch (Exception ex) {
            Log.e(TAG, "Unable to parse attributes due to: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            a.recycle();
        }
    }

    /**
     * Initializes the Icon and TextEdit.
     */
    private void initialize() {
        Log.d(TAG, "initialize()");

        boolean isEmailAddress = false;
        // Mandatory parameters
        this.setOrientation(LinearLayout.HORIZONTAL);

        // Create the Icon
        if (_icon == null) {
            _icon = new ImageView(this.getContext());
            _icon.setLayoutParams(
                    new LayoutParams(0, LayoutParams.MATCH_PARENT, ICON_WEIGHT)
            );
            _icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            if (_iconResource != null && _iconResource != 0) {
                _icon.setImageResource(_iconResource);
            }

            this.addView(_icon);
        }

        // Create the EditText
        if (_editText == null) {
            _editText = new EditText(this.getContext());
            _editText.setLayoutParams(
                    new LayoutParams(0, LayoutParams.MATCH_PARENT, EDIT_TEXT_WEIGHT)
            );

            if (_hint != null) {
                _editText.setHint(String.format("%s%s", HINT_PREFIX, _hint));
            }
            if (_inputType == 1) {
                _editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else if (_inputType == 2) {
                _editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                isEmailAddress = true;
            } else if (_inputType == 3) {
                _editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                _editText.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
            }


            if (mMaxLenth == 100 || mMaxLenth == 0) {
                mMaxLenth = 100;
            }
            if (!isEmailAddress) {
                _editText.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(mMaxLenth)});
            }
            this.addView(_editText);
        }
    }

    /**
     * Convenience Accessor to the underlying EditText's 'getText()' method.
     *
     * @return
     */
    public Editable getText() {
        return getEditText().getText();
    }

    public void setText(String text) {
        getEditText().setText(text);
    }

    public void setTypeface(Typeface ttf) {
        getEditText().setTypeface(ttf);
    }

    /**
     * Returns the underlying EditText.
     *
     * @return
     */
    public EditText getEditText() {
        return _editText;
    }

    /**
     * Returns the underlying ImageView displaying the icon.
     *
     * @return
     */
    public ImageView getImageView() {
        return _icon;
    }

    InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; ++i) {
                if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890 ]*").matcher(String.valueOf(source.charAt(i))).matches()) {
                    return "";
                }
            }

            return null;
        }
    };

}
