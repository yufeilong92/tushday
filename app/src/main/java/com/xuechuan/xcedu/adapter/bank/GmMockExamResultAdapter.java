package com.xuechuan.xcedu.adapter.bank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.GmReadColorManger;
import com.xuechuan.xcedu.utils.GmTextColorUtil;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter.bank
 * @Description: 答题卡布局
 * @author: L-BackPacker
 * @date: 2018.12.25 上午 11:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class GmMockExamResultAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<?> mListDatas;
    private LayoutInflater mInflater;
    private GmReadColorManger mColorManger;
    private ArrayList<DoBankSqliteVo> mDoLists;
    private final GmTextColorUtil mUtil;

    public GmMockExamResultAdapter(Context mContext, List<?> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
        mUtil = GmTextColorUtil.get_Instance(mContext);
        mInflater = LayoutInflater.from(mContext);
    }

    public void doEventColor(GmReadColorManger colorManger) {
        if (colorManger == null) return;
        this.mColorManger = colorManger;
        notifyDataSetChanged();
    }

    public void doEventListDatas(ArrayList<DoBankSqliteVo> list) {
        if (list == null || list.isEmpty()) return;
        this.mDoLists = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MockExamViewHolde(mInflater.inflate(R.layout.eockexam_result_recycler_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MockExamViewHolde mViewholder = (MockExamViewHolde) holder;
        QuestionSqliteVo vo = (QuestionSqliteVo) mListDatas.get(position);
        switch (vo.getQuestiontype()) {
            case 2:
            case 3:

                break;
            case 4:
                break;
            case 5:
                break;

        }

    }

    @Override
    public int getItemCount() {
        return mListDatas.size();
    }

    public class MockExamViewHolde extends RecyclerView.ViewHolder {
        public GridView mGvGmMockexampopContent;
        public TextView mTvGmMockexamTitle;
        public GridView mGvGmTextContent;
        public LinearLayout mLlGmMockexamLayout;

        public MockExamViewHolde(View itemView) {
            super(itemView);
            this.mGvGmMockexampopContent = (GridView) itemView.findViewById(R.id.gv_gm_mockexampop_content);
            this.mTvGmMockexamTitle = (TextView) itemView.findViewById(R.id.tv_gm_mockexam_title);
            this.mGvGmTextContent = (GridView) itemView.findViewById(R.id.gv_gm_text_content);
            this.mLlGmMockexamLayout = (LinearLayout) itemView.findViewById(R.id.ll_gm_mockexam_layout);
        }
    }

}
