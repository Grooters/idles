package io.github.grooters.idles.view.dialog;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Objects;
import io.github.grooters.idles.Presenter.IConfigP;
import io.github.grooters.idles.Presenter.imple.ConfigP;
import io.github.grooters.idles.R;
import io.github.grooters.idles.base.BaseDialog;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.dialog.inter.IConfigDilog;

public class ConfigDialog extends BaseDialog implements IConfigDilog, View.OnClickListener {

    private IConfigP iConfigP;

    private EditText localUrlEdit, netUrlEdit, authorizeChangeUrlEdit;

    @Override
    public int getLayout() {

        return R.layout.dialog_config;

    }

    @Override
    public void initView(View view) {

        setTransaction();

        iConfigP = new ConfigP(this);

        Button setting = view.findViewById(R.id.butt_config);

        localUrlEdit = view.findViewById(R.id.edit_local_url);

        netUrlEdit = view.findViewById(R.id.edit_net_url);

        authorizeChangeUrlEdit = view.findViewById(R.id.edit_authorize_change_url);

        setting.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.butt_config:

                iConfigP.setUrl(Objects.requireNonNull(getActivity()).getApplicationContext(),
                        localUrlEdit.getText().toString(), netUrlEdit.getText().toString(), authorizeChangeUrlEdit.getText().toString());

                break;


        }

    }

    @Override
    public void configFailure(String message) {

        Toaster.shortShow(getActivity(), message);

    }

    @Override
    public void configSuccess(String localUrl, String netUrl) {

        Toaster.shortShow(getActivity(), "设置成功");

    }
}
