package io.github.grooters.idles.view.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import io.github.grooters.idles.R;
import io.github.grooters.idles.base.BaseFragment;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.fragment.inter.IAccountFragment;

public class AccountFragment extends BaseFragment implements View.OnClickListener, IAccountFragment {

    private EditText passwordEdit, passwordSecondEdit;

    @Override
    public int getLayout() {
        return R.layout.fragment_account;
    }

    @Override
    public void initView(View view) {

        EditText phoneEdit = view.findViewById(R.id.edit_phone);

        EditText verificationEdit = view.findViewById(R.id.edit_verification);

        passwordEdit = view.findViewById(R.id.edit_password);

        passwordSecondEdit = view.findViewById(R.id.edit_password_second);

        TextView verificationTextView = view.findViewById(R.id.text_verification);

        TextView verifyTextView = view.findViewById(R.id.text_verify);

        phoneEdit.setOnClickListener(this);

        verificationEdit.setOnClickListener(this);

        passwordEdit.setOnClickListener(this);

        passwordSecondEdit.setOnClickListener(this);

        verifyTextView.setOnClickListener(this);

        verificationTextView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.text_verify:

            case R.id.text_verification:

            case R.id.text_register:

        }

    }

    @Override
    public void setPasswordEditVisible() {

        Toaster.shortShow(getActivity(),"验证成功");

        passwordEdit.setVisibility(View.VISIBLE);

        passwordSecondEdit.setVisibility(View.VISIBLE);

    }

    @Override
    public void showVerificationError() {

        Toaster.shortShow(getActivity(),"验证失败");

    }
}
