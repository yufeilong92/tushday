package com.xuechuan.xcedu.ui.bank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.bank.GmGridOneViewAdapter;
import com.xuechuan.xcedu.adapter.bank.GmMockExamGridViewAdapter;
import com.xuechuan.xcedu.adapter.bank.GmMockExamResultAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.event.GmChangerColorEvent;
import com.xuechuan.xcedu.fragment.GmReadOneFragment;
import com.xuechuan.xcedu.mvp.view.TimeShowView;
import com.xuechuan.xcedu.sqlitedb.DoBankSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.LookSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.QuestionExamrelarionSqlitHelp;
import com.xuechuan.xcedu.sqlitedb.QuestionSqliteHelp;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.EncryptionUtil;
import com.xuechuan.xcedu.utils.GmReadColorManger;
import com.xuechuan.xcedu.utils.GmTextUtil;
import com.xuechuan.xcedu.utils.MyTimeUitl;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.TimeTools;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuesitonExamRaltionSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;
import com.xuechuan.xcedu.weight.ReaderViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: MockExamActivity
 * @Package com.xuechuan.xcedu.ui.bank
 * @Description: 模拟考试
 * @author: L-BackPacker
 * @date: 2018.12.24 上午 11:16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.12.24
 */
public class MockExamActivity extends BaseActivity implements View.OnClickListener, GmReadOneFragment.notification, TimeShowView {
    private static String PARAMT_KEY = "com.xuechuan.xcedu.ui.MockExamActivity.courserid";
    /**
     * 题干id
     */
    private static String PARAMT_QUESITON_ID = "com.xuechuan.xcedu.ui.MockExamActivity.quesiton_id";

    private String mCourseId;
    private Context mContext;
    private GmTextUtil mTextUtil;
    private DoBankSqliteHelp mDoBankHelp;
    private LookSqliteHelp mLookSqliteHelp;
    private DialogUtil mDialogUtil;
    private ArrayList<QuestionSqliteVo> lists;
    private GmReadOneFragment mReadFragment;
    private QuestionSqliteVo mQuestionSqliteVo;
    private GmReadColorManger mColorManger;
    private QuestionSqliteHelp mSqliteHelp;
    private CommonPopupWindow mPopAnswer;
    private ImageView mIvTextBarBack;
    private ImageView mIvTextBarTimeImg;
    private TextView mActivityTitleText;
    private LinearLayout mLlTextBarTitle;
    private ImageView mIvTextBarDelect;
    private ImageView mIvTextBarMore;
    private LinearLayout mLlTextTitleBar;
    private View mVGmReadLine;
    private ReaderViewPager mReaderViewPager;
    private ImageView mShadowView;
    private FrameLayout mFlContentLayoutOne;
    private View mVGmBarLine;
    private TextView mTvTextCollect;
    private ImageView mIvTextMenu;
    private TextView mTvTextQid;
    private TextView mTvTextAllqid;
    private TextView mTvTextShare;
    private LinearLayout mLiTextNavbar;
    private LinearLayout mLlNewtextBar;
    private LinearLayout mModeexamLlLayout;
    private String mQuesitoId;
    private QuestionExamrelarionSqlitHelp mExamrelarionSqlitHelp;
    //用于判断用户是否交卷
    private boolean mSubmit = false;
    private MyTimeUitl mTimeUitl;
    private long mCoundData;
    private AlertDialog mStopDialog;
    private CommonPopupWindow mPopResult;
    /**
     * 用户使用时间
     */
    private String mUserDoTime;


    /**
     * @param context
     * @param courseid 科目
     */
    public static Intent start_Intent(Context context, String courseid, String question_id) {
        Intent intent = new Intent(context, MockExamActivity.class);
        intent.putExtra(PARAMT_KEY, courseid);
        intent.putExtra(PARAMT_QUESITON_ID, question_id);
        return intent;
    }

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_test);
        initView();
    }*/


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_exam_test);

        if (getIntent() != null) {
            mCourseId = getIntent().getStringExtra(PARAMT_KEY);
            //题目对应id
            mQuesitoId = getIntent().getStringExtra(PARAMT_QUESITON_ID);
        }
        initView();
        initUtils();
        //设置倒计时
        initTime();
        //获取数据
        lists = getList();
        //初始化翻页效果
        initReadViewPager();
        //显示下表
        initData(0);

    }

    private void initTime() {
        if (mCourseId.equals(DataMessageVo.COURESID_CASE)) {//案例
            mTimeUitl.start(mActivityTitleText, 3, 0, 0, this);
        } else if (mCourseId.equals(DataMessageVo.COURESID_SKILL)) {//技术
            mTimeUitl.start(mActivityTitleText, 2, 30, 0, this);
        } else if (mCourseId.equals(DataMessageVo.COURESID_SYNTHESIZE)) {//综合
            mTimeUitl.start(mActivityTitleText, 2, 30, 0, this);
        }
        setPauseRestartImg(true, R.mipmap.qbank_answer_icon_pau);
    }

    private ArrayList<QuestionSqliteVo> getList() {
        ArrayList<QuesitonExamRaltionSqliteVo> vos = mExamrelarionSqlitHelp.queryExamRaltionVo(mQuesitoId);
        ArrayList<QuestionSqliteVo> list = mSqliteHelp.queryQuestionVo(vos);
        if (list == null) return new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                QuestionSqliteVo vo = list.get(i);
                String d = EncryptionUtil.D(vo.getQuestion());
                vo.setQuestionStr(d);
                String keyword = EncryptionUtil.D(vo.getKeywords());
                vo.setKeywordStr(keyword);
                String explain = EncryptionUtil.D(vo.getExplained());
                vo.setExplainedStr(explain);
            }
        }
        return list;
    }

    private void initUtils() {
        //做题工具类
        mTextUtil = GmTextUtil.get_Instance(mContext);
        //做题记录表
        mDoBankHelp = DoBankSqliteHelp.get_Instance(mContext);
        //查看表
        mLookSqliteHelp = LookSqliteHelp.get_Instance(mContext);
        //对话框
        mDialogUtil = DialogUtil.getInstance();
        //题库信息表
        mSqliteHelp = QuestionSqliteHelp.get_Instance(mContext);
        //考试关联表
        mExamrelarionSqlitHelp = QuestionExamrelarionSqlitHelp.get_Instance(mContext);
        //时间控制器
        mTimeUitl = MyTimeUitl.getInstance(mContext);
    }

    /*  public ArrayList<QuestionSqliteVo> getLists() {
          ArrayList<QuestionSqliteVo> list = mSqliteHelp.queryAllQuesitonWithCouresid(mCourseId);
          if (list != null && !list.isEmpty()) {
              for (int i = 0; i < list.size(); i++) {
                  QuestionSqliteVo vo = list.get(i);
                  String d = EncryptionUtil.D(vo.getQuestion());
                  vo.setQuestionStr(d);
                  String keyword = EncryptionUtil.D(vo.getKeywords());
                  vo.setKeywordStr(keyword);
                  String explain = EncryptionUtil.D(vo.getExplained());
                  vo.setExplainedStr(explain);
              }
          }
          return list;
      }
  */
    private void initData(int index) {
        mTvTextAllqid.setText(String.valueOf(lists.size()));
        mTvTextQid.setText(String.valueOf(++index));
    }

    private int prePosition2;
    private int curPosition2;

    private void initReadViewPager() {
        mReaderViewPager.setAdapter(new GmFragmentAdpater(getSupportFragmentManager(), mContext, lists, mCourseId));
        mReaderViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mShadowView.setTranslationX(mReaderViewPager.getWidth() - positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                curPosition2 = position;
                prePosition2 = curPosition2;
                initData(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mReaderViewPager.setOffscreenPageLimit(1);
    }

    private void initView() {
        mContext = this;
        mIvTextBarBack = (ImageView) findViewById(R.id.iv_text_bar_back);
        mIvTextBarBack.setOnClickListener(this);
        mIvTextBarTimeImg = (ImageView) findViewById(R.id.iv_text_bar_time_img);
        mActivityTitleText = (TextView) findViewById(R.id.activity_title_text);
        mLlTextBarTitle = (LinearLayout) findViewById(R.id.ll_text_bar_title);
        mLlTextBarTitle.setOnClickListener(this);
        mIvTextBarDelect = (ImageView) findViewById(R.id.iv_text_bar_delect);
        mIvTextBarDelect.setOnClickListener(this);
        mIvTextBarMore = (ImageView) findViewById(R.id.iv_text_bar_more);
        mIvTextBarMore.setOnClickListener(this);
        mLlTextTitleBar = (LinearLayout) findViewById(R.id.ll_text_title_bar);
        mLlTextTitleBar.setOnClickListener(this);
        mVGmReadLine = (View) findViewById(R.id.v_gm_read_line);
        mVGmReadLine.setOnClickListener(this);
        mReaderViewPager = (ReaderViewPager) findViewById(R.id.readerViewPager);
        mReaderViewPager.setOnClickListener(this);
        mShadowView = (ImageView) findViewById(R.id.shadowView);
        mShadowView.setOnClickListener(this);
        mFlContentLayoutOne = (FrameLayout) findViewById(R.id.fl_content_layout_one);
        mFlContentLayoutOne.setOnClickListener(this);
        mVGmBarLine = (View) findViewById(R.id.v_gm_bar_line);
        mVGmBarLine.setOnClickListener(this);
        mTvTextCollect = (TextView) findViewById(R.id.tv_text_collect);
        mTvTextCollect.setOnClickListener(this);
        mIvTextMenu = (ImageView) findViewById(R.id.iv_text_menu);
        mIvTextMenu.setOnClickListener(this);
        mTvTextQid = (TextView) findViewById(R.id.tv_text_qid);
        mTvTextQid.setOnClickListener(this);
        mTvTextAllqid = (TextView) findViewById(R.id.tv_text_allqid);
        mTvTextAllqid.setOnClickListener(this);
        mTvTextShare = (TextView) findViewById(R.id.tv_text_share);
        mTvTextShare.setOnClickListener(this);
        mLiTextNavbar = (LinearLayout) findViewById(R.id.li_text_navbar);
        mLiTextNavbar.setOnClickListener(this);
        mLlNewtextBar = (LinearLayout) findViewById(R.id.ll_newtext_bar);
        mLlNewtextBar.setOnClickListener(this);
        mModeexamLlLayout = (LinearLayout) findViewById(R.id.modeexam_ll_layout);
        mModeexamLlLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_text_menu://菜单
                //判断用户是否提交 显示不同答题卡布局
                if (mSubmit) {//显示答案解析
                    showResultPopLayout();
                } else {//显示未交接界面
                    showAnswerCardLayout();
                }
                break;
            case R.id.iv_text_bar_more://更多
                if (mReadFragment != null) {
                    mReadFragment.showGmSetting();
                }
                break;
            case R.id.iv_text_bar_back://返回
                showBackFinishDialog();
                break;
            case R.id.ll_text_bar_title://时间
                setPauseRestartImg(true, R.mipmap.qbank_answer_icon_cont);
                pauseTime();
                showPauseDialog();
                break;


        }
    }

    //返回键处理
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_UP) {
            showBackFinishDialog();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void showBackFinishDialog() {
        mDialogUtil.showTitleDialog(mContext, "确定退出答题", "退出答题"
                , "取消", true);
        mDialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
            @Override
            public void onSureClickListener() {
                finish();
            }

            @Override
            public void onCancelClickListener() {
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseTime();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mSubmit) return;
        if (mTimeUitl != null) {
            if (mStopDialog == null || !mStopDialog.isShowing()) {
                mStopDialog = mDialogUtil.showStopDialog(mContext);
                setStopDialogListener(mDialogUtil);
                setPauseRestartImg(true, R.mipmap.qbank_answer_icon_cont);
            }
        }
    }

    //显示暂停对话框
    private void showPauseDialog() {
        mStopDialog = mDialogUtil.showStopDialog(mContext);
        setStopDialogListener(mDialogUtil);
    }

    private void setStopDialogListener(DialogUtil util) {
        util.setStopClickListener(new DialogUtil.onStopClickListener() {
            @Override
            public void onNextClickListener() {
                dismissDialog(mStopDialog);
                setPauseRestartImg(true, R.mipmap.qbank_answer_icon_pau);
                resumeTime();
            }
        });
    }

    private void setPauseRestartImg(boolean isShow, int id) {
        mIvTextBarTimeImg.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mIvTextBarTimeImg.setImageDrawable(getDrawable(mContext, id));
    }

    private void resumeTime() {
        if (mTimeUitl != null)
            mTimeUitl.resume();
    }

    private void pauseTime() {
        if (mTimeUitl != null)
            mTimeUitl.pause();
    }

    private void cancelTime() {
        if (mTimeUitl != null)
            mTimeUitl.cancel();
    }

    private void restartTime() {
        if (mTimeUitl != null)
            mTimeUitl.restart();
    }

    /**
     * 设置答题卡布局
     */
    private void showAnswerCardLayout() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;

        mPopAnswer = new CommonPopupWindow(this, R.layout.pop_item_answer, ViewGroup.LayoutParams.MATCH_PARENT, (int) (screenHeight * 0.7)) {
            private Button mBtnSubmit;
            private TextView mTvPopNew;
            private TextView mTvLine;
            private TextView mTvTextPopERROR;
            private TextView mTvTextPopRight;
            private TextView mTvTextPopRegression;
            private TextView mTvPopCount;
            private RecyclerView mRlvPopContent;
            private GridView mGvPopContent;
            private LinearLayout mLLPopLayout;

            @Override
            protected void initView() {
                View view = getContentView();
                mTvPopNew = (TextView) view.findViewById(R.id.tv_pop_new);
                mTvLine = (TextView) view.findViewById(R.id.tv_line);
                mTvPopCount = (TextView) view.findViewById(R.id.tv_pop_count);
                mRlvPopContent = view.findViewById(R.id.rlv_pop_content);
                mGvPopContent = view.findViewById(R.id.gv_pop_content);
                mBtnSubmit = (Button) view.findViewById(R.id.btn_pop_answer_sumbit);
                mLLPopLayout = (LinearLayout) view.findViewById(R.id.ll_pop_layout);
                mTvTextPopRight = (TextView) view.findViewById(R.id.tv_text_pop_right);
                mTvTextPopERROR = (TextView) view.findViewById(R.id.tv_text_pop_error);
                mTvTextPopRegression = (TextView) view.findViewById(R.id.tv_text_pop_regression);
                mBtnSubmit.setVisibility(View.VISIBLE);
            }

            @Override
            protected void initEvent() {
                if (mColorManger != null) {
                    mTvPopNew.setTextColor(mColorManger.getmTextTitleColor());
                    mTvPopCount.setTextColor(mColorManger.getmTextTitleColor());
                    mBtnSubmit.setTextColor(mColorManger.getmTextTitleColor());
                    mLLPopLayout.setBackgroundColor(mColorManger.getmLayoutBgColor());
                    mTvTextPopRight.setTextColor(mColorManger.getmTextFuColor());
                    mTvTextPopRegression.setTextColor(mColorManger.getmTextFuColor());
                    mTvTextPopERROR.setTextColor(mColorManger.getmTextFuColor());
                    mTvLine.setTextColor(mColorManger.getmTextTitleColor());
                }
                mTvPopNew.setText(String.valueOf(curPosition2 + 1));
                mTvPopCount.setText(String.valueOf(lists.size()));
                final ArrayList<DoBankSqliteVo> datas = findAllDoDatas();
                bindGridViewAdapter(datas);
                mBtnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //交卷 提交为true
                        mSubmit = true;
                        pauseTime();
                        //判断用户是否做完
                        doUserSumbitDialog(datas.size() == lists.size());
                        //暂停时间
                        setPauseRestartImg(true, R.mipmap.qbank_answer_icon_cont);
                    }
                });
            }

            @Override
            protected void initWindow() {
                super.initWindow();
                PopupWindow instance = getPopupWindow();
                instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mTextUtil.setBackgroundAlpha(1f, MockExamActivity.this);
                    }
                });
            }

            private void bindGridViewAdapter(ArrayList<DoBankSqliteVo> datas) {
                GmMockExamGridViewAdapter adapter = new GmMockExamGridViewAdapter(mContext, lists);
                adapter.doEventListDatas(datas);
                adapter.doEventColor(mColorManger);
                adapter.doCurrentPostion(curPosition2);
                mGvPopContent.setAdapter(adapter);
                mGvPopContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        curPosition2 = position;
                        mReaderViewPager.setCurrentItem(position);
                        mPopAnswer.getPopupWindow().dismiss();
                    }
                });
            }
        };
        mPopAnswer.showAtLocation(mModeexamLlLayout, Gravity.BOTTOM, 0, 0);
        mTextUtil.setBackgroundAlpha(0.5f, MockExamActivity.this);
    }

    //无小题答题卡布局
    public void showNewResultPopLayout() {
        mPopResult = new CommonPopupWindow(this, R.layout.pop_new_result, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) {
            private LinearLayout mLlGmmockexamLayout;
            private View mViewGmMockexamLine;
            private LinearLayout mLlMockexamBar;
            private Button mBtnMockexanJiexi;
            private Button mBtnMockexanAgain;
            private RecyclerView mRlvMockexamResult;
            private TextView mTvMockexamRightContent;
            private TextView mTvMockexamRightTitle;
            private TextView mTvMockexamDotimeTitleContent;
            private TextView mTvMockexamDotimeTitle;
            private TextView mTvResultNumber;

            @Override
            protected void initView() {
                View view = getContentView();

                mLlGmmockexamLayout = (LinearLayout) view.findViewById(R.id.ll_gmmockexamdialog_layout);
                mViewGmMockexamLine = (View) view.findViewById(R.id.view_gm_mockexam_line);
                //下标题
                mLlMockexamBar = (LinearLayout) view.findViewById(R.id.ll_mockexam_bar);
                mBtnMockexanJiexi = (Button) view.findViewById(R.id.btn_mockexan_jiexi);
                mBtnMockexanAgain = (Button) view.findViewById(R.id.btn_mockexan_again);
                mRlvMockexamResult = (RecyclerView) view.findViewById(R.id.rlv_mockexam_result);
                mTvMockexamRightContent = (TextView) view.findViewById(R.id.tv_mockexam_right_content);
                mTvMockexamRightTitle = (TextView) view.findViewById(R.id.tv_mockexam_right_title);
                mTvMockexamDotimeTitle = (TextView) view.findViewById(R.id.tv_mockexam_dotime_title);
                mTvMockexamDotimeTitleContent = (TextView) view.findViewById(R.id.tv_mockexam_dotime_title_content);
                mTvResultNumber = (TextView) view.findViewById(R.id.tv_result_number);

            }

            @Override
            protected void initEvent() {
                setGridLayoutManger(mContext, mRlvMockexamResult, 1);
                GmMockExamResultAdapter adapter = new GmMockExamResultAdapter(mContext, lists);
                mRlvMockexamResult.setAdapter(adapter);
                adapter.doEventColor(mColorManger);
                adapter.doEventListDatas(findAllDoDatas());
                setBtnListener(mBtnMockexanAgain, 1);
                setBtnListener(mBtnMockexanJiexi, 2);

            }

            private void doDialogColor() {
                if (mColorManger == null) {
                    return;
                }
                mTvResultNumber.setTextColor(mColorManger.getmTextTitleColor());
                mTvMockexamDotimeTitleContent.setTextColor(mColorManger.getmTextTitleColor());
                mTvMockexamDotimeTitle.setTextColor(mColorManger.getmTextFuColor());
                mTvMockexamRightTitle.setTextColor(mColorManger.getmTextFuColor());
                mTvMockexamRightContent.setTextColor(mColorManger.getmTextTitleColor());
                mLlMockexamBar.setBackgroundColor(mColorManger.getmLayoutBgColor());
                mBtnMockexanAgain.setTextColor(mColorManger.getmTextTitleColor());
                mBtnMockexanJiexi.setTextColor(mColorManger.getmTextTitleColor());
                mViewGmMockexamLine.setBackgroundColor(mColorManger.getmCutLineColor());
                mLlGmmockexamLayout.setBackgroundColor(mColorManger.getmLayoutBgColor());
            }

            @Override
            protected void initWindow() {
                super.initWindow();
                PopupWindow instance = getPopupWindow();
                instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mTextUtil.setBackgroundAlpha(1f, MockExamActivity.this);
                    }
                });
            }
        };
        mPopResult.showAtLocation(mModeexamLlLayout, Gravity.BOTTOM, 0, 0);
        mTextUtil.setBackgroundAlpha(0.5f, MockExamActivity.this);
    }

    //无小题答题卡布局
    public void showResultPopLayout() {
        mPopResult = new CommonPopupWindow(this, R.layout.pop_result, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) {
            private LinearLayout mLlGmmockexamLayout;
            private View mViewGmMockexamLine;
            private LinearLayout mLlMockexamBar;
            private Button mBtnMockexanJiexi;
            private Button mBtnMockexanAgain;
            private GridView mGvMockexamResult;
            private TextView mTvMockexamRightContent;
            private TextView mTvMockexamRightTitle;
            private TextView mTvMockexamDotimeTitleContent;
            private TextView mTvMockexamDotimeTitle;
            private TextView mTvResultNumber;

            @Override
            protected void initView() {
                View view = getContentView();

                mLlGmmockexamLayout = (LinearLayout) view.findViewById(R.id.ll_gmmockexamdialog_layout);
                mViewGmMockexamLine = (View) view.findViewById(R.id.view_gm_mockexam_line);
                //下标题
                mLlMockexamBar = (LinearLayout) view.findViewById(R.id.ll_mockexam_bar);
                mBtnMockexanJiexi = (Button) view.findViewById(R.id.btn_mockexan_jiexi);
                mBtnMockexanAgain = (Button) view.findViewById(R.id.btn_mockexan_again);
                mGvMockexamResult = (GridView) view.findViewById(R.id.gv_mockexam_result_bag);
                mTvMockexamRightContent = (TextView) view.findViewById(R.id.tv_mockexam_right_content);
                mTvMockexamRightTitle = (TextView) view.findViewById(R.id.tv_mockexam_right_title);
                mTvMockexamDotimeTitle = (TextView) view.findViewById(R.id.tv_mockexam_dotime_title);
                mTvMockexamDotimeTitleContent = (TextView) view.findViewById(R.id.tv_mockexam_dotime_title_content);
                mTvResultNumber = (TextView) view.findViewById(R.id.tv_result_number);

            }

            @Override
            protected void initEvent() {
                GmGridOneViewAdapter adapter = new GmGridOneViewAdapter(mContext, lists);
                mGvMockexamResult.setAdapter(adapter);
                adapter.doEventColor(mColorManger);
                ArrayList<DoBankSqliteVo> list = findAllDoDatas();
                adapter.doEventListDatas(list);
                setBtnListener(mBtnMockexanAgain, 1);
                setBtnListener(mBtnMockexanJiexi, 2);
                bindData(list);
            }

            private void bindData(ArrayList<DoBankSqliteVo> list) {
                //正确率
                String accuracy = mTextUtil.getRihgtAccuracy(list, lists.size());
                mTvMockexamRightContent.setText(accuracy);
                mTvMockexamDotimeTitleContent.setText(mUserDoTime);
                //分说
                String grade = mTextUtil.getUserGrade(list);
                mTvResultNumber.setText(grade);
            }

            private void doDialogColor() {
                if (mColorManger == null) {
                    return;
                }
                mTvResultNumber.setTextColor(mColorManger.getmTextTitleColor());
                mTvMockexamDotimeTitleContent.setTextColor(mColorManger.getmTextTitleColor());
                mTvMockexamDotimeTitle.setTextColor(mColorManger.getmTextFuColor());
                mTvMockexamRightTitle.setTextColor(mColorManger.getmTextFuColor());
                mTvMockexamRightContent.setTextColor(mColorManger.getmTextTitleColor());
                mLlMockexamBar.setBackgroundColor(mColorManger.getmLayoutBgColor());
                mBtnMockexanAgain.setTextColor(mColorManger.getmTextTitleColor());
                mBtnMockexanJiexi.setTextColor(mColorManger.getmTextTitleColor());
                mViewGmMockexamLine.setBackgroundColor(mColorManger.getmCutLineColor());
                mLlGmmockexamLayout.setBackgroundColor(mColorManger.getmLayoutBgColor());
            }

            @Override
            protected void initWindow() {
                super.initWindow();
                PopupWindow instance = getPopupWindow();
                instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mTextUtil.setBackgroundAlpha(1f, MockExamActivity.this);
                    }
                });
            }
        };
        mPopResult.showAtLocation(mModeexamLlLayout, Gravity.BOTTOM, 0, 0);
        mTextUtil.setBackgroundAlpha(0.5f, MockExamActivity.this);
    }

    /**
     * 设置监听
     *
     * @param button
     * @param number 1为重新，2 为解析
     */
    private void setBtnListener(Button button, final int number) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopResult != null && mPopResult.getPopupWindow().isShowing()) {
                    mPopResult.getPopupWindow().dismiss();
                }
                switch (number) {
                    case 1://重新
                        mSubmit = false;
                        //图标
                        setPauseRestartImg(true, R.mipmap.qbank_answer_icon_pau);
                        //重置时间
                        restartTime();
                        //删除做题记录
                        mDoBankHelp.delelteTable();
                        curPosition2 = 0;
                        //跳转
                        mReaderViewPager.setCurrentItem(0);
                        EventBus.getDefault().postSticky(new GmChangerColorEvent(0, 3, false));
                        break;
                    case 2://查看解析
                        mSubmit = true;
                        EventBus.getDefault().postSticky(new GmChangerColorEvent(0, 3, false));
                        break;

                }
            }
        });
    }

    private ArrayList<DoBankSqliteVo> findAllDoDatas() {
        ArrayList<DoBankSqliteVo> list = new ArrayList<>();
        //查询用户数据
        ArrayList<DoBankSqliteVo> doBankSqliteVos = mDoBankHelp.finDAllUserDoText();
        if (doBankSqliteVos == null || doBankSqliteVos.isEmpty()) return list;
        for (int i = 0; i < lists.size(); i++) {
            QuestionSqliteVo vo = lists.get(i);
            for (int k = 0; k < doBankSqliteVos.size(); k++) {
                DoBankSqliteVo sqliteVo = doBankSqliteVos.get(k);
                if (vo.getQuestion_id() == sqliteVo.getQuestion_id()) {
                    list.add(sqliteVo);
                }
            }
        }
        return list;
    }

    /**
     * 交卷对话框
     *
     * @param isOver
     */
    public void doUserSumbitDialog(boolean isOver) {
        if (mPopAnswer != null && mPopAnswer.getPopupWindow().isShowing()) {
            mPopAnswer.getPopupWindow().dismiss();
        }
        pauseTime();
        mDialogUtil.setIsDoOver(isOver);
        mDialogUtil.showSubmitDialog(mContext);
        mDialogUtil.setSubmitClickListener(new DialogUtil.onSubmitClickListener() {
            @Override
            public void onCancelClickListener() {
                mSubmit = false;
            }

            @Override
            public void oSubmitClickListener() {
                //获取用户使用时间
                getUserTime();
                //取消时间
                cancelTime();
                mSubmit = true;
                setPauseRestartImg(true, R.mipmap.qbank_answer_icon_pau);
                //时间布局
                setLinearBar(false);
                //显示答案界面
                showResultPopLayout();


            }
        });
    }

    public void getUserTime() {
        if (mTimeUitl != null) {
            long time = mTimeUitl.getNubmer();
            if (time != 0) {
                mUserDoTime = TimeTools.getCountTimeByLong(time);
            }
        }
    }

    //时间结束回调
    @Override
    public void TimeFinish() {
        pauseTime();
        showSubmitDialog();
    }

    private void showSubmitDialog() {
        pauseTime();
        mDialogUtil.showTitleDialog(mContext, "考试时间结束,请交卷", "交卷", "取消", false);
        mDialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
            @Override
            public void onSureClickListener() {
                //获取用户使用时间
                getUserTime();
                cancelTime();
                //时间布局
                setLinearBar(false);
                mSubmit = true;
                setPauseRestartImg(true, R.mipmap.qbank_answer_icon_cont);

            }

            @Override
            public void onCancelClickListener() {
                mSubmit = false;
            }
        });
    }

    private void setLinearBar(boolean clickable) {
        mLlTextBarTitle.setClickable(clickable);
    }

    public class GmFragmentAdpater extends FragmentPagerAdapter {

        private final String mCourseid;
        private Context mContext;
        private List<?> mListDatas;


        public GmFragmentAdpater(FragmentManager fm, Context mContext, List<?> mListDatas, String coursid) {
            super(fm);
            this.mListDatas = mListDatas;
            this.mContext = mContext;
            this.mCourseid = coursid;
        }

        @Override
        public Fragment getItem(int position) {
            if (mListDatas.size() == 0) {
                mReadFragment = GmReadOneFragment.newInstance(null, position, mCourseid);
            } else {
                mQuestionSqliteVo = (QuestionSqliteVo) mListDatas.get(position);

                mReadFragment = GmReadOneFragment.newInstance(mQuestionSqliteVo, position, mCourseid);
            }
            return mReadFragment;
        }

        @Override
        public int getCount() {
            if (mListDatas.size() == 0) {
                return 1;
            } else
                return mListDatas.size();
        }
    }

    @Override
    public void saveUserDoLog(DoBankSqliteVo vo) {
        mDoBankHelp.addDoBankItem(vo);

    }

    @Override
    public DoBankSqliteVo getUserDoLog(int quesiton_id) {
        if (mDoBankHelp == null) return null;
        return mDoBankHelp.queryWQid(quesiton_id);
    }

    @Override
    public void deleteUserDolog(int quesiton_id) {
        mDoBankHelp.deleteBankWithQuestid(quesiton_id);
    }

    /**
     * 下一题
     */
    @Override
    public void doRightGo() {
        if (mReaderViewPager == null) return;
        int currentItem = mReaderViewPager.getCurrentItem();
        currentItem = currentItem + 1;
        if (currentItem < 0) {
            T.showToast(mContext, "已经是最后一题");
            currentItem = 0;
        }
        mReaderViewPager.setCurrentItem(currentItem, true);
    }

    @Override
    public void changerColor(GmReadColorManger colorManger) {
        this.mColorManger = colorManger;
        mLiTextNavbar.setBackgroundColor(colorManger.getmLayoutBgColor());
        mTvTextCollect.setTextColor(colorManger.getmTextFuColor());
        mTvTextShare.setTextColor(colorManger.getmTextFuColor());
        mTvTextQid.setTextColor(colorManger.getmTextRedColor());
        mTvTextAllqid.setTextColor(colorManger.getmTextRedColor());
        mLlTextTitleBar.setBackgroundColor(colorManger.getmLayoutBgColor());
        mActivityTitleText.setTextColor(colorManger.getmTextTitleColor());
        mLlTextTitleBar.setBackgroundColor(colorManger.getmLayoutBgColor());
        mVGmReadLine.setBackgroundColor(colorManger.getmCutLineColor());
        //标题
        mLlTextTitleBar.setBackgroundColor(colorManger.getmLayoutBgColor());
        mVGmBarLine.setBackgroundColor(colorManger.getmCutLineColor());

    }

    //用于记录用户是否提交
    @Override
    public void setSubmitAble(boolean submit) {
        this.mSubmit = submit;
    }

    @Override
    public boolean getSubmitAble() {
        return this.mSubmit;
    }


    @Override
    public DoBankSqliteVo queryUserData(int qustion_id) {
        if (mDoBankHelp == null) return null;
        return mDoBankHelp.queryWQid(qustion_id);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDoBankHelp != null) {
            mDoBankHelp.delelteTable();
        }
        doDoText();
        //清空倒计时
        cancelTime();

    }

    private void doDoText() {
        if (curPosition2 == 0) return;
        if (mQuestionSqliteVo != null) {
/*            UserInfomDbHelp help = UserInfomDbHelp.get_Instance(mContext);
            UserInfomSqliteVo vo = help.findUserInfomVo();
            LookSqliteVo lookSqliteVo = new LookSqliteVo();
            lookSqliteVo.setChapterid(mQuestionSqliteVo.getChapter_id());
            lookSqliteVo.setKid(mQuestionSqliteVo.getCourseid());
            lookSqliteVo.setTextid(mQuestionSqliteVo.getQuestion_id());
            lookSqliteVo.setRightnumber(String.valueOf(curPosition2 + 1));
            lookSqliteVo.setUserid(String.valueOf(vo.getSaffid()));
            lookSqliteVo.setRightAllNumber(String.valueOf(lists.size()));
            mLookSqliteHelp.addDoLookItem(lookSqliteVo);*/
        }
    }
}
