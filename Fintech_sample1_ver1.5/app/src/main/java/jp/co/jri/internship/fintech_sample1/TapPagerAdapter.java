package jp.co.jri.internship.fintech_sample1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TapPagerAdapter extends FragmentStateAdapter {

    String buttonName2;

    public TapPagerAdapter(Main2Activity fragment, String buttonName1){
        super(fragment);
        buttonName2 = buttonName1;        // 遷移元のボタン名を受け取る
    }

    // 指定されたタブの位置（position）に対応するタブページ（Fragment）を作成する
    @NonNull
    @Override
    public Fragment createFragment(int position){
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        // tab1選択時
        if (position == 0) {
            fragment = new Tab1Fragment();
            bundle.putString("ButtonName", buttonName2); // 遷移先で表示するため遷移元のボタン名をBundleに格納
        }
        // tab2選択時
        else if (position == 1){
            fragment = new Tab2Fragment();
        }
        assert fragment != null;
        fragment.setArguments(bundle);
        return fragment;
    }

    //タブの数を返す
    @Override
    public int getItemCount(){
      return 2;
    }
}