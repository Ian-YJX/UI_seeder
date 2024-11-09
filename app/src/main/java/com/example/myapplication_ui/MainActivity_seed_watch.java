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

import com.example.myapplication_ui.Menu.MainActivity_Menu;

public class MainActivity_seed_watch extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // 启用边缘到边缘模式
        setContentView(R.layout.activity_main_seed_watch); // 设置布局文件

        // 设置窗口Insets适配
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 从Intent获取传递的数据
        String name = getIntent().getStringExtra("name");
        String stockDistance = getIntent().getStringExtra("stockDistance");
        String sowingAmount = getIntent().getStringExtra("sowingAmount");
        String singleSowingRate = getIntent().getStringExtra("singleSowingRate");
        String reSowingRate = getIntent().getStringExtra("reSowingRate");
        String missSowingRate = getIntent().getStringExtra("missSowingRate");
        String deviationRate = getIntent().getStringExtra("deviationRate");
        String current = getIntent().getStringExtra("current");
        String lightIntensity = getIntent().getStringExtra("lightIntensity");
        String status = getIntent().getStringExtra("status");

        // 获取表格布局
        TableLayout tableLayout = findViewById(R.id.tableLayout);

        // 查找表格中的第二行，通常是第一行数据行（在第一行是标题行的情况下）
        TableRow secondRow = (TableRow) tableLayout.getChildAt(1);  // 获取第二行

        if (secondRow != null) {
            // 更新第二行的内容
            updateRowWithValues(secondRow, name,stockDistance, sowingAmount, singleSowingRate,
                    reSowingRate, missSowingRate, deviationRate, current, lightIntensity, status);
        }

        // 设置导航按钮
        setupNavigationButtons();
    }

    // 辅助方法：更新行数据
    private void updateRowWithValues(TableRow row,  String name, String stockDistance, String sowingAmount,
                                     String singleSowingRate, String reSowingRate,
                                     String missSowingRate, String deviationRate,
                                     String current, String lightIntensity, String status) {
        // 更新每一列的值
        ((TextView) row.getChildAt(0)).setText(name); // 株距
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
        ImageButton seed_watch_backtomenu = findViewById(R.id.seed_watch_backtomenu);
        seed_watch_backtomenu.setOnClickListener(v -> {
            Intent intent_back_tomenu = new Intent(MainActivity_seed_watch.this, MainActivity_Menu.class);
            startActivity(intent_back_tomenu);
        });

        ImageButton seed_watch_backtohome = findViewById(R.id.seed_watch_backtohome);
        seed_watch_backtohome.setOnClickListener(v -> {
            Intent intent_back_to_home = new Intent(MainActivity_seed_watch.this, MainActivity_home.class);
            startActivity(intent_back_to_home);
        });

        ImageButton seed_watch_gotointent = findViewById(R.id.seed_watch_gotointent);
        seed_watch_gotointent.setOnClickListener(v -> {
            Intent intent_to_data_input = new Intent(MainActivity_seed_watch.this, DataInputActivity.class);
            startActivity(intent_to_data_input);
        });
    }
}
