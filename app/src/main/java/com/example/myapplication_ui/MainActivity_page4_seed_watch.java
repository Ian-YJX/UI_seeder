package com.example.myapplication_ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication_ui.utils.CANReceiving;

public class MainActivity_page4_seed_watch extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // 启用边缘到边缘模式
        setContentView(R.layout.activity_main_page4_seed_watch); // 设置布局文件

        // 设置窗口Insets适配，适应系统栏（如状态栏、导航栏）的显示
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()); // 获取系统栏的Insets
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom); // 为视图设置适当的内边距
            return insets;
        });

        // 从Intent获取传递的数据
        String stockDistance = getIntent().getStringExtra("stockDistance");//株距
        String sowingAmount = getIntent().getStringExtra("sowingAmount");//播种量
        String singleSowingRate = getIntent().getStringExtra("singleSowingRate");//单播率
        String reSowingRate = getIntent().getStringExtra("reSowingRate");//重播率
        String missSowingRate = getIntent().getStringExtra("missSowingRate");//漏播率
        String deviationRate = getIntent().getStringExtra("deviationRate");//编变差
        String current = getIntent().getStringExtra("current");//电流
        String lightIntensity = getIntent().getStringExtra("lightIntensity");//光强
        String status = getIntent().getStringExtra("status");//状态

        // 获取表格布局
        TableLayout tableLayout = findViewById(R.id.tableLayout);

        // 查找表格中的第二行
        TableRow secondRow = (TableRow) tableLayout.getChildAt(1);

        if (secondRow != null) {
            // 如果第二行不为空，更新该行的数据
            updateRowWithValues(secondRow, stockDistance, sowingAmount, singleSowingRate,
                    reSowingRate, missSowingRate, deviationRate, current, lightIntensity, status);
        }

        // 设置导航按钮
        setupNavigationButtons();
    }

    // 辅助方法：更新表格行的数据
    private void updateRowWithValues(TableRow row, String stockDistance, String sowingAmount,
                                     String singleSowingRate, String reSowingRate,
                                     String missSowingRate, String deviationRate,
                                     String current, String lightIntensity, String status) {
        // 更新第二行每个列的文本内容
        ((TextView) row.getChildAt(1)).setText(stockDistance); // 株距
        ((TextView) row.getChildAt(2)).setText(sowingAmount); // 播种量
        ((TextView) row.getChildAt(3)).setText(singleSowingRate); // 单播率
        ((TextView) row.getChildAt(4)).setText(reSowingRate); // 重播率
        ((TextView) row.getChildAt(5)).setText(missSowingRate); // 漏播率
        ((TextView) row.getChildAt(6)).setText(deviationRate); // 变差率
        ((TextView) row.getChildAt(7)).setText(current); // 电流
        ((TextView) row.getChildAt(8)).setText(lightIntensity); // 光强
        ((TextView) row.getChildAt(9)).setText(status); // 状态
    }

    // 设置导航按钮的辅助方法
    private void setupNavigationButtons() {
        // 设置返回到菜单按钮的点击事件
        ImageButton seed_watch_backtomenu = findViewById(R.id.seed_watch_backtomenu);
        seed_watch_backtomenu.setOnClickListener(v -> {
            // 点击时启动返回菜单的Intent
            Intent intent_back_tomenu = new Intent(MainActivity_page4_seed_watch.this, MainActivity_page1_menu.class);
            startActivity(intent_back_tomenu);
        });

        // 设置返回到首页按钮的点击事件
        ImageButton seed_watch_backtohome = findViewById(R.id.seed_watch_backtohome);
        seed_watch_backtohome.setOnClickListener(v -> {
            // 点击时启动返回首页的Intent
            Intent intent_back_to_home = new Intent(MainActivity_page4_seed_watch.this, MainActivity_page0_home.class);
            startActivity(intent_back_to_home);
        });

        // 设置跳转到数据输入页面的按钮点击事件
        ImageButton seed_watch_gotointent = findViewById(R.id.seed_watch_gotointent);
        seed_watch_gotointent.setOnClickListener(v -> {
            // 点击时启动跳转到数据输入页面的Intent
            Intent intent_to_data_input = new Intent(MainActivity_page4_seed_watch.this, CANReceiving.class);
            startActivity(intent_to_data_input);
        });
    }
}
