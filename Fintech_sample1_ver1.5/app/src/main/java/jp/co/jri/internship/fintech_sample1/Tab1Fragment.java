package jp.co.jri.internship.fintech_sample1;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Tab1Fragment extends Fragment {
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        // レイアウト（tab1_fragment.xml）を作成する
        View v =inflater.inflate(R.layout.tab1_fragment,container,false);

        // TapPagerAdapterから遷移元のボタン名（buttonName）を受け取り、レイアウトに表示する
        String buttonName = getArguments().getString("ButtonName");
        TextView textView = v.findViewById(R.id.tapButton);
        textView.setText( buttonName + "がタップされました。");

        return v;
        }
    }
