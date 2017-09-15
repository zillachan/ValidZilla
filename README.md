# ValidZilla
Android InputLayout validation

## How to use

```
repositories {
        jcenter()
        maven { url "https://jitpack.io" }
   }
   dependencies {
         compile 'com.github.zillachan:ValiZilla:0.1'
   }
```

## Step1
Inject your TextInputLayout with @NotNull or @Reg

## Step2
Inject your callback method with @ValidSuccess

## Setp3

Call ValiZilla.vali(Object target);