package pub.zilla.example;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.zilla.validzilla.ValiZilla;
import pub.zilla.validzilla.an.NotNull;
import pub.zilla.validzilla.an.Reg;
import pub.zilla.validzilla.an.ValiSuccess;

public class MainActivity extends AppCompatActivity {

    @NotNull(order = 1, error = R.string.input_need)
    @Reg(order = 2, error = R.string.reg_error, reg = "^0{0,1}(13[0-9]|15[7-9]|153|156|18[7-9])[0-9]{8}$")
    @BindView(R.id.inputLayout)
    TextInputLayout inputLayout;

    @NotNull(order = 3, error = R.string.input_need)
    @Reg(order = 4, error = R.string.reg_error, reg = "^[0-9][0-9]{5}$")
    @BindView(R.id.zipcodeLayout)
    TextInputLayout zipcodeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button, R.id.button_first})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                ValiZilla.vali(this);
                break;
            case R.id.button_first:
                ValiZilla.vali(this, 1, 2);
                break;
        }
    }

    @ValiSuccess
    void onValiSuccess() {
        Toast.makeText(this, R.string.vali_success, Toast.LENGTH_LONG).show();
    }

    @ValiSuccess
    void onValiSuccess(int... i) {
        Toast.makeText(this, R.string.vali_success, Toast.LENGTH_LONG).show();
    }
}
