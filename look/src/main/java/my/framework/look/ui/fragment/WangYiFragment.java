package my.framework.look.ui.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 网易头条
 */
public class WangYiFragment extends BaseFragment {

    private TextView mTextView;

    @Override
    public View initView() {
        mTextView = new TextView(getActivity());
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextSize(20);
        mTextView.setTextColor(Color.BLACK);

        return mTextView;
    }

    @Override
    public void initData() {
        Toast.makeText(getActivity(), "加载了最新数据", Toast.LENGTH_SHORT).show();
        mTextView.setText("最新新闻");
    }


}
