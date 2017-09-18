# ValidZilla
Android TextInputLayout validation

[![](https://jitpack.io/v/zillachan/ValidZilla.svg)](https://jitpack.io/#zillachan/ValidZilla)
## How to use

```
repositories {
        jcenter()
        maven { url "https://jitpack.io" }
   }
   dependencies {
         compile 'com.github.zillachan:ValiZilla:1.0'
   }
```

Example

```
public class MainActivity extends AppCompatActivity {

    @Reg(error = R.string.reg_error,reg = "^0{0,1}(13[0-9]|15[7-9]|153|156|18[7-9])[0-9]{8}$")
    @NotNull(error = R.string.input_need)
    @BindView(R.id.inputLayout)
    TextInputLayout inputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        ValiZilla.vali(this);
    }

    @ValiSuccess
    void onValiSuccess(){
        Toast.makeText(this,R.string.vali_success,Toast.LENGTH_LONG).show();
    }
}

```
## Step1
Inject your TextInputLayout with @NotNull or @Reg

## Step2
Inject your callback method with @ValidSuccess

## Setp3

Call ValiZilla.vali(Object target);

# Screenshots

![Image](https://raw.githubusercontent.com/zillachan/ValidZilla/master/images/valizilla.png)

