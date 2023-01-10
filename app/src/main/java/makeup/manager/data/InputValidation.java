package makeup.manager.data;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;


public class InputValidation {
    private Context context;

    /**
     * constructor
     *
     * @param context
     */
    public InputValidation(Context context) {
        this.context = context;
    }

    /**
     * method to check InputEditText filled .
     *
     * @param EditText
     * @param textInputLayout
     * @param message
     * @return
     */
    public boolean isInputEditTextFilled(EditText EditText, TextInputLayout textInputLayout, String message) {
        String value = EditText.getText().toString().trim();
        if (value.isEmpty()) {
            textInputLayout.setError(message);
            hideKeyboardFrom(EditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }


    /**
     * method to check InputEditText has valid email .
     *
     * @param EditText
     * @param textInputLayout
     * @param message
     * @return
     */
    public boolean isInputEditTextEmail(EditText EditText, TextInputLayout textInputLayout, String message) {
        String value = EditText.getText().toString().trim();
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            textInputLayout.setError(message);
            hideKeyboardFrom(EditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean isInputEditTextMatches(EditText EditText1, EditText EditText2, TextInputLayout textInputLayout, String message) {
        String value1 = EditText1.getText().toString().trim();
        String value2 = EditText2.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            textInputLayout.setError(message);
            hideKeyboardFrom(EditText2);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * method to Hide keyboard
     *
     * @param view
     */
    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
