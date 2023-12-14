package jp.co.jri.internship.fintech_sample1;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
// 下位のバージョンにも対応させる場合はsupport-v4パッケージを使用します

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
public class Tab2Fragment extends Fragment {
    BarChart chart;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // レイアウト（tab2_fragment.xml）を作成する
        View v = inflater.inflate(R.layout.tab2_fragment, container, false);

        /*



        return v;
    }
 */
    return v;
    }
    private TextView mTextView;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TextViewをひも付けます
        mTextView = (TextView) view.findViewById(R.id.textView);
        // Buttonのクリックした時の処理を書きます
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText(mTextView.getText() + "!");
            }
        });

        int sumWin = 1;
        int sumLoss = 1;
        int sumDraw = 1;

        // 棒グラフ表示領域を取得
        chart = view.findViewById(R.id.barChart);

        // 棒グラフに勝敗数、あいこ数のデータをセット。getBarDataの関数は別途定義
        BarData data = new BarData(getBarData(sumWin,sumLoss,sumDraw));
        chart.setData(data);

        //Y軸(左)
        YAxis left = chart.getAxisLeft();
        left.setAxisMinimum(0f);
        left.setAxisMaximum(20f);
        left.setDrawTopYLabelEntry(true);

        //Y軸(右)
        YAxis right = chart.getAxisRight();
        right.setDrawLabels(false);
        right.setDrawGridLines(false);
        right.setDrawZeroLine(true);
        right.setDrawTopYLabelEntry(true);

        //X軸
        XAxis xAxis = chart.getXAxis();
        //X軸に表示するLabelのリスト(最初の""は原点の位置)
        final String[] labels = {"","勝ち", "負け", "あいこ"};

        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        XAxis bottomAxis = chart.getXAxis();
        bottomAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        bottomAxis.setDrawLabels(true);
        bottomAxis.setDrawGridLines(false);
        bottomAxis.setDrawAxisLine(true);

        // グラフ上の表示
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.setClickable(false);

        // 凡例を非表示
        chart.getLegend().setEnabled(false);

        chart.setScaleEnabled(false);
        // アニメーション
        chart.animateY(1200, Easing.Linear);
    }

    private List<IBarDataSet> getBarData(int sumWin, int sumLoss, int sumDraw){
        // 表示させるデータをリストに格納
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, sumWin));
        entries.add(new BarEntry(2, sumLoss));
        entries.add(new BarEntry(3, sumDraw));

        // 棒グラフ表示用のデータセットに準備したリストをセット
        BarDataSet dataSet = new BarDataSet(entries, "bar");

        //ハイライトさせない
        dataSet.setHighlightEnabled(false);

        // 棒グラフの色をセット
        dataSet.setColors(new int[]{R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark}, getActivity());

        List<IBarDataSet> bars = new ArrayList<>();
        bars.add(dataSet);

        return bars;
    }
}