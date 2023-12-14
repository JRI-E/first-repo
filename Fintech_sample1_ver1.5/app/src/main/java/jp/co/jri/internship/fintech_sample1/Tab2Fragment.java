package jp.co.jri.internship.fintech_sample1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.SimpleAdapter;
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
        BarData data = new BarData(getBarData(view,sumWin,sumLoss,sumDraw));
        chart.setData(data);

        //Y軸(左)
        YAxis left = chart.getAxisLeft();
        left.setAxisMinimum(0f);
        left.setAxisMaximum(100000f);
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
        final String[] labels = {"","消費", "収入", "テスト"};

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

    private List<IBarDataSet> getBarData(View view,int sumWin, int sumLoss, int sumDraw){
        // 表示させるデータをリストに格納
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, clickBtSearch(1)));
        entries.add(new BarEntry(2, clickBtSearch(2)));
        entries.add(new BarEntry(3, sumDraw));

        TextView tvTest = (TextView) view.findViewById(R.id.textView);
        tvTest.setText(String.format("expense:%d income:%d",clickBtSearch(1),clickBtSearch(2)));


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

    public int clickBtSearch(int flag) {

        // 収支の集計領域を準備する
        int sumIncome = 0;  // 収入の集計領域
        int sumExpense = 0; // 支出の集計領域

        // 対象期間の始まりと終わりを入力する
        //EditText etStart = view.findViewById(R.id.etStart);     // Viewにプログラムでの変数名を割り当てる
        //EditText etEnd = view.findViewById(R.id.etEnd);       // Viewにプログラムでの変数名を割り当てる
        //String startDate = etStart.getText().toString();   // EditTextに入力した内容を文字列にして変数に渡す
        //String endDate = etEnd.getText().toString();// EditTextに入力した内容を文字列にして変数に渡す

        String startDate = "2022/07/01";   // EditTextに入力した内容を文字列にして変数に渡す
        String endDate = "2022/08/31";


        // 表示用のList(fintechDataList)を用意する
        List<FintechData> fintechDataList = new ArrayList<>();

        // LocalFintechDateBase.txt(Androidローカルファイル)が存在しない場合、FintechDataBase.csvからデータを読み込み、
        // LocalFintechDateBase.txtに書き出したのち、List(fintechDataList)を作成する
        // LocalFintechDateBase.txtが存在する場合、ローカルファイルからList(fintechDataList)を作成する
        CsvReader parser = new CsvReader();
        String filename = "LocalFintechDateBase.txt";
        File file = getActivity().getFileStreamPath(filename);
        parser.readerFintechDataBase(getActivity().getApplicationContext(), file.exists());

        // text.txt(Androidローカルファイル)から1行取り出し、表示用のList(fintechDataList)に入れる
        // 上記をtext.txtが終わるまで繰り返す
        for (FintechData fdata : parser.fintechObjects) {
            // transDateがstartDateから、endDateまでの日付のデータのみListに入れる
            if (fdata.getTransDate().compareTo(startDate) >= 0) {
                if (fdata.getTransDate().compareTo(endDate) <= 0) {
                    fintechDataList.add(fdata);
                    // 収支の合計を集計する
                    if (fdata.getAmount() >= 0) {
                        sumIncome = sumIncome + fdata.getAmount();   // 収入の合計を求める
                    } else {
                        sumExpense = sumExpense + fdata.getAmount(); // 支出の合計を求める
                    }
                }
            }
        }

        // Adapterに表示用のList(fintechDataList)を受け渡す
        List<Map<String, ?>> listData = fintechDataToMapList(fintechDataList);  // Adapterに渡す形式のlist型変数の宣言と初期化
        SimpleAdapter adapter = new SimpleAdapter(                  // ()内で指定した内容のAdapterを生成する
                getActivity(),
                listData,                                           // ListView用に自作したレイアウトにFintechDataのどの項目を表示するかを指定する
                R.layout.custom_list_layout,                        // 自作したレイアウト名
                new String[]{"transDate", "content", "amount"},     // 表示するFintechDataの項目を指定
                new int[]{R.id.tvList1, R.id.tvList2, R.id.tvList3} // 自作したレイアウトのViewのidを指定
        );

        if (flag == 1){
            return sumExpense;
        }else {
            return sumIncome;
        }

    }

    // Adapterに渡す形式のlist型変数の宣言と初期化
    private List<Map<String, ?>> fintechDataToMapList(List<FintechData> fintechDataList) {
        List<Map<String, ?>> data = new ArrayList<>();
        for (FintechData fintechData : fintechDataList) {
            data.add(fintechDataToMap(fintechData));
        }
        return data;
    }

    // Adapterに渡す形式のlist型変数の宣言と初期化（詳細）
    @SuppressLint("DefaultLocale")
    private Map<String, ?> fintechDataToMap(FintechData fintechData) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", fintechData.getId());
        map.put("transDate", fintechData.getTransDate());
        map.put("transTime", fintechData.getTransTime());
        map.put("service", fintechData.getService());
        map.put("category", fintechData.getCategory());
        map.put("supplier", fintechData.getSupplier());
        map.put("content", fintechData.getContent());
        map.put("use", fintechData.getUse());
        map.put("amount", String.format("%,d", fintechData.getAmount()));
        map.put("balance", String.format("%,d", fintechData.getBalance()));
        return map;
    }
}