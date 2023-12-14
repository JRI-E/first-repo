package jp.co.jri.internship.fintech_sample1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CombinedChart combinedChart = view.findViewById(R.id.combinedChart);
        // 収入と支出のデータ（棒グラフ）
        BarData barData = new BarData(getBarData(view)/* your BarDataSet */);

// 収入と支出の和のデータ（折れ線グラフ）
        LineData lineData = new LineData(getLineData(view));

// CombinedData にデータを追加
        CombinedData combinedData = new CombinedData();
        combinedData.setData(barData);
        combinedData.setData(lineData);

// CombinedChart に CombinedData を設定
        combinedChart.setData(combinedData);

        //表示データ取得
        BarData data = new BarData(getBarData(view));
        chart.setData(data);

        //Y軸(左)
        YAxis left = chart.getAxisLeft();
        left.setAxisMinimum(-600000);
        left.setAxisMaximum(1000000);
        left.setLabelCount(6);
        left.setDrawTopYLabelEntry(true);
        //整数表示に

        //Y軸(右)
        YAxis right = chart.getAxisRight();
        right.setDrawLabels(false);
        right.setDrawGridLines(false);
        right.setDrawZeroLine(true);
        right.setDrawTopYLabelEntry(true);

        //X軸
        XAxis xAxis = chart.getXAxis();
        //X軸に表示するLabelのリスト(最初の""は原点の位置)
        final String[] labels = {"","7月", "8月", "9月"};

        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        XAxis bottomAxis = chart.getXAxis();
        bottomAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        bottomAxis.setDrawLabels(true);
        bottomAxis.setDrawGridLines(false);
        bottomAxis.setDrawAxisLine(true);

        //グラフ上の表示
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.setClickable(false);

        //凡例
        chart.getLegend().setEnabled(false);

        chart.setScaleEnabled(true);
        //アニメーション
        chart.animateY(1200, Easing.Linear);

    }

    private List<IBarDataSet> getBarData(View view){
        // 表示させるデータをリストに格納
        int a = clickBtSearch(1);
        int b = clickBtSearch(2);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, new float[]{clickBtSearch(1),clickBtSearch(2)}));
        entries.add(new BarEntry(2, new float[]{clickBtSearch(3),clickBtSearch(4)}));
        entries.add(new BarEntry(3, new float[]{clickBtSearch(5),clickBtSearch(6)}));

        TextView tvTest = (TextView) view.findViewById(R.id.textView2);
        tvTest.setText(String.format("expense:%d income:%d",a,b));


        // 棒グラフ表示用のデータセットに準備したリストをセット
        BarDataSet dataSet = new BarDataSet(entries, "bar");

        //ハイライトさせない
        dataSet.setHighlightEnabled(false);

        // 棒グラフの色をセット
        dataSet.setColors(new int[]{R.color.colorAccent, R.color.colorPrimary}, getActivity());

        List<IBarDataSet> bars = new ArrayList<>();
        bars.add(dataSet);

        return bars;
    }

    private LineDataSet  getLineData(View view){
        // 表示させるデータをリストに格納

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, clickBtSearch(7)));
        entries.add(new Entry(2, clickBtSearch(8)));
        entries.add(new Entry(3, clickBtSearch(9)));

        // データセットの作成
        LineDataSet dataSet = new LineDataSet(entries, "line");

        // データセットのスタイル設定（任意）
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(10f);

        // データセットをリストに追加
        List<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        return dataSet;
    }

    public int clickBtSearch(int flag) {

        // 収支の集計領域を準備する
        int sumIncomeSeven = 0;  // 収入の集計領域
        int sumExpenseSeven = 0; // 支出の集計領域

        int sumIncomeEight = 0;  // 収入の集計領域
        int sumExpenseEight = 0; // 支出の集計領域

        int sumIncomeNine = 0;  // 収入の集計領域
        int sumExpenseNine = 0; // 支出の集計領域



        String startDateSeven = "2022/07/01";   // EditTextに入力した内容を文字列にして変数に渡す
        String endDateSeven = "2022/07/31";

        String startDateEight = "2022/08/01";   // EditTextに入力した内容を文字列にして変数に渡す
        String endDateEight = "2022/08/31";

        String startDateNine = "2022/09/01";   // EditTextに入力した内容を文字列にして変数に渡す
        String endDateNine = "2022/09/31";


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
            if (fdata.getTransDate().compareTo(startDateSeven) >= 0) {
                if (fdata.getTransDate().compareTo(endDateSeven) <= 0) {
                    fintechDataList.add(fdata);
                    // 収支の合計を集計する
                    if (fdata.getAmount() >= 0) {
                        sumIncomeSeven = sumIncomeSeven + fdata.getAmount();   // 収入の合計を求める
                    } else {
                        sumExpenseSeven = sumExpenseSeven + fdata.getAmount(); // 支出の合計を求める
                    }
                }
            }
        }

        for (FintechData fdata : parser.fintechObjects) {
            // transDateがstartDateから、endDateまでの日付のデータのみListに入れる
            if (fdata.getTransDate().compareTo(startDateEight) >= 0) {
                if (fdata.getTransDate().compareTo(endDateEight) <= 0) {
                    fintechDataList.add(fdata);
                    // 収支の合計を集計する
                    if (fdata.getAmount() >= 0) {
                        sumIncomeEight = sumIncomeEight + fdata.getAmount();   // 収入の合計を求める
                    } else {
                        sumExpenseEight = sumExpenseEight + fdata.getAmount(); // 支出の合計を求める
                    }
                }
            }
        }
        for(FintechData fdata : parser.fintechObjects){
            if (fdata.getTransDate().compareTo(startDateNine) >= 0) {
                if (fdata.getTransDate().compareTo(endDateNine) <= 0) {
                    fintechDataList.add(fdata);
                    // 収支の合計を集計する
                    if (fdata.getAmount() >= 0) {
                        sumIncomeNine = sumIncomeNine + fdata.getAmount();   // 収入の合計を求める
                    } else {
                        sumExpenseNine = sumExpenseNine + fdata.getAmount(); // 支出の合計を求める
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

        int resSeven = sumExpenseSeven + sumIncomeSeven;//7月収支の合計
        int resEight = sumExpenseEight + sumIncomeEight;//8月収支の合計
        int resNine = sumExpenseNine + sumIncomeNine;//9月収支の合計


        if (flag == 1){
            return sumExpenseSeven;
        }else if(flag == 2){
            return sumIncomeSeven;
        }
        else if(flag == 3){
            return sumExpenseEight;
        }else if(flag == 4){
            return sumIncomeEight;
        }
        else if(flag == 5){
            return sumExpenseNine;
        }else if(flag == 6){
            return sumIncomeNine;
        }
        else if(flag == 7){
            return sumExpenseSeven;
        }else if(flag == 8){
            return sumExpenseEight;
        }else if(flag == 9){
            return sumExpenseNine;
        }
        else {
            return 0;
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