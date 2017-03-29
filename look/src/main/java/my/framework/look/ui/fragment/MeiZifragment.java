package my.framework.look.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import my.framework.look.R;
import my.framework.look.model.meizi.MeiZi;
import my.framework.look.ui.activity.PictureActivity;
import my.framework.look.util.MyOkhttp;

/**
 * 每日看看
 */
public class MeiZiFragment extends BaseFragment {

    private RecyclerView mRecycleMeizi;
    private ProgressBar mProgress;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int lastVisibleItem;
    private List<MeiZi> mMeiziData;
    private int page = 1;
    private MyAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    public View initView() {

        View view = View.inflate(getActivity(), R.layout.meizi_fragment_layout, null);
        mRecycleMeizi = (RecyclerView) view.findViewById(R.id.recycle_meizi);
        mProgress = (ProgressBar) view.findViewById(R.id.prograss);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.line_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.tab_bg, R.color.red, R.color.red);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecycleMeizi.setLayoutManager(mLayoutManager);
        setListener();
        return view;

    }


    /**
     * 初始化数据
     */
    @Override
    public void initData() {

        new GetData().execute("http://gank.io/api/data/福利/10/1");

    }


    //下拉刷新
    private void setListener() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                new GetData().execute("http://gank.io/api/data/福利/10/1");
            }
        });

        mRecycleMeizi.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //0：当前屏幕停止滚动；1 时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；
                //2 时：随用户的操作，屏幕上产生的惯性滑动；
                //滑动状态停止并且剩余两个item时自动加载
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 2 >= mLayoutManager.getItemCount()) {
                    new GetData().execute("http://gank.io/api/data/福利/10/" + (++page));
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 获取加载的最后一个可见视图在适配器的位置。
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });

    }


    class GetData extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //设置swipeRefreshLayout为刷新状态
            swipeRefreshLayout.setRefreshing(true);

        }

        @Override
        protected String doInBackground(String... params) {
            return MyOkhttp.get(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (!TextUtils.isEmpty(result)) {
                JSONObject jsonObject;
                Gson gson = new Gson();
                String jsonData = null;
                try {
                    jsonObject = new JSONObject(result);
                    jsonData = jsonObject.getString("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mMeiziData == null || mMeiziData.size() == 0) {
                    mMeiziData = gson.fromJson(jsonData, new TypeToken<List<MeiZi>>() {
                    }.getType());
                    MeiZi pages = new MeiZi();
                    pages.setPage(page);
                    mMeiziData.add(pages);
                } else {
                    List<MeiZi> more = gson.fromJson(jsonData, new TypeToken<List<MeiZi>>() {
                    }.getType());
                    mMeiziData.addAll(more);//加载更多
                    MeiZi pages = new MeiZi();
                    pages.setPage(page);
                    mMeiziData.add(pages);
                }

                if (mAdapter == null) {
                    //recyclerview设置适配器
                    mRecycleMeizi.setAdapter(mAdapter = new MyAdapter());

                } else {

                    mAdapter.notifyDataSetChanged();
                }

            }
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    public void startActivity() {


    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener, View.OnLongClickListener {


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.grid_meizi_item, parent, false);
            MyViewHolder holder = new MyViewHolder(view);

            //将创建的View注册点击事件
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Glide.with(mContext).load(mMeiziData.get(position).getUrl()).into(holder.imageView);//加载网络图片
        }

        @Override
        public int getItemCount() {
            return mMeiziData.size();
        }

        @Override
        public void onClick(View v) {

            int position = mRecycleMeizi.getChildAdapterPosition(v);
            String url = mMeiziData.get(position).getUrl();

            Intent intent = new Intent(getActivity(), PictureActivity.class);
            if (url != null) {
                intent.putExtra("url", url);
                startActivity(intent);
            }

        }

        @Override
        public boolean onLongClick(View v) {

            Toast.makeText(getActivity(), "ddd", Toast.LENGTH_SHORT).show();
            return false;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;

            public MyViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.iv);

            }
        }

    }
}
