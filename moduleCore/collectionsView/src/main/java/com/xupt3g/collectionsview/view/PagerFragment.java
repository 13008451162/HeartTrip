package com.xupt3g.collectionsview.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xupt3g.collectionsview.R;
import com.xupt3g.collectionsview.collectionModel.retrofit.CollectionData;
import com.xupt3g.collectionsview.guessModel.retrofit.GuessData;
import com.xupt3g.collectionsview.presenter.CollectionPresenter;
import com.xupt3g.mylibrary1.LoginStatusData;
import com.xuexiang.xui.widget.button.RippleView;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.mylibrary1.implservice.CollectionManagerService;
import com.xupt3g.mylibrary1.response.IsSuccessfulResponse;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.view.PagerFragment
 *
 * @author: shallew
 * @data:2024/2/16 2:21
 * @about: TODO ViewPager的子页
 */
public class PagerFragment extends Fragment implements CollectionsGuessManagerImpl {
    private String dataText;

    public static PagerFragment newInstance(String param) {
        //在主函数中调用该方法 传入数据，并创建Fragment的实例
        PagerFragment viewFragment = new PagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", param);
        viewFragment.setArguments(bundle);
        return viewFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建实例后会在这个方法取出数据
        if (getArguments() != null) {
            dataText = getArguments().getString("data");
        }
        //注册EventBus订阅
        EventBus.getDefault().register(this);
    }

    public static final String PAGE_COLLECTIONS = "colonEDlections";
    public static final String PAGE_GUESS = "guess";
    private SwipeRecyclerView swipeRecyclerView;
    /**
     * 记录获取到的收藏集合数据，为了方便之后的添加和删除操作
     */
    private List<CollectionData> collections;
    /**
     * 记录获取到的猜你喜欢集合数据，为了方便之后的添加和删除操作
     */
    private List<GuessData> guesses;
    private CollectionsListAdapter adapter;
    private RecommendationsListAdapter adapter2;
    /**
     * 收藏数据展示布局
     */
    private ConstraintLayout collectionsRecyclerLayout;
    /**
     * 缺省页布局
     */
    private ConstraintLayout noCollectionsView;
    private ImageView noCollectionsImg;
    private RippleView rippleView;
    private TextView noCollectionsText;
    private View footerView;
    private View headerView;
    private TextView headerText;
    /**
     * 碎片布局
     */
    private View view;

    private CollectionPresenter presenter;

    private FloatingActionButton refreshButton;
    private TextView defaultButtonText;


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.collection_frag_pager_swiperecycler, container, false);

        //缺省页布局的变量声明
        noCollectionsView = view.findViewById(R.id.no_collections_view);
        noCollectionsImg = view.findViewById(R.id.img_show);
        rippleView = view.findViewById(R.id.ripple_to_recommendations);
        noCollectionsText = view.findViewById(R.id.no_collections_hint);
        defaultButtonText = view.findViewById(R.id.default_button_text);

        noCollectionsView.setVisibility(View.GONE);

        //数据展示页面的变量声明
        collectionsRecyclerLayout = view.findViewById(R.id.collections_recycler_layout);
        footerView = inflater.inflate(R.layout.collection_recycler_footview, container, false);
        headerView = inflater.inflate(R.layout.collection_recycler_footview, container, false);
        headerText = (TextView) headerView.findViewById(R.id.bottom_text);
        swipeRecyclerView = (SwipeRecyclerView) view.findViewById(R.id.swiperecyclerview);

        refreshButton = (FloatingActionButton) view.findViewById(R.id.fab);
        refreshButton.setVisibility(View.GONE);

        presenter = new CollectionPresenter(this);

        if (PAGE_COLLECTIONS.equals(dataText)) {
            LoginStatusData.getLoginStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
                        //如果未登录 加载frag_not_login view
                        defaultViewInit(R.drawable.collection_not_login, "您还没有登录，请先完成登录！", "点我去登录");
                    } else {
                        //如果已登录 还分两种情况：收藏集合为空，收藏集合不为空
                        //先使用Presenter获取到收藏集合数据在判断
                        //已登录才会去使用Presenter层的方法获取收藏集合
                        presenter.showCollections();

                    }
                }
            });
        } else {
            //使用Presenter层的方法获取猜你喜欢数据集合
            presenter.showGuessList();
        }

        return view;
    }

    @Override
    public void showCollectionsList(List<CollectionData> collectionsDataList) {
        collections = collectionsDataList;

        if (collectionsDataList == null || collectionsDataList.size() == 0) {
            //如果收藏集合为空 加载frag_no_login view
            defaultViewInit(R.drawable.collection_no_collections, "未找到您的收藏，去看看我们的推荐吧！", "点击看我推荐");
        } else {
            //如果收藏集合不为空 加载frag_pager_swiperecycler view
            collectionViewInit();
        }
    }

    /**
     * TODO 收藏列表中没有数据时UI初始化
     */
    private void defaultViewInit(int imgDrawable, String text, String btnText) {
        //没有收藏
        noCollectionsView.setVisibility(View.VISIBLE);
        Glide.with(requireContext()).asGif().load(imgDrawable).into(noCollectionsImg);
        noCollectionsText.setText(text);
        defaultButtonText.setText(btnText);
        collectionsRecyclerLayout.setVisibility(View.GONE);
        //这里调用回调方法
        rippleView.setOnClickListener(view1 -> mrvcListener.onClicked());
    }


    @SuppressLint("SetTextI18n")
    private void collectionViewInit() {
        noCollectionsView.setVisibility(View.GONE);
        collectionsRecyclerLayout.setVisibility(View.VISIBLE);

        if (swipeRecyclerView.getAdapter() != null) {
            //已有适配器 不能重复设置监听 只更新数据
            adapter = new CollectionsListAdapter(collections);
            swipeRecyclerView.setAdapter(adapter);
            swipeRecyclerView.scheduleLayoutAnimation();
            return ;
        }

        OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int position) {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu();
                //先记录民宿ID
                int houseId = collections.get(position).getId();
                //先向服务器提交删除申请Presenter
                presenter.removeCollection(houseId);
            }
        };
        adapter = new CollectionsListAdapter(collections);
        headerText.setText("以下共" + collections.size() + "条数据~");
        viewInit(collections, R.drawable.collection_toolbar_background, "确认删除",
                mItemMenuClickListener, adapter, new GridLayoutManager(getContext(), 1));


        animationInit(R.anim.collection_recycler_anim);
    }

    private void animationInit(int animRes) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), animRes);
        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation);
        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
        layoutAnimationController.setDelay(0.2f);
        swipeRecyclerView.setLayoutAnimation(layoutAnimationController);
    }

    /**
     * @param houseId 民宿ID
     *                  TODO 当用户从收藏页面取消收藏时，该收藏是否是从猜你喜欢中添加的。
     *                      如果是就在猜你喜欢页面中将该民宿恢复成未收藏的样子。
     *                     注意：该方法只在GuessPage的实例中调用
     */
    @SuppressLint("NotifyDataSetChanged")
    private void cancelCollectInGuesses(int houseId) {
        if (guesses != null) {
            for (int i = 0; i < guesses.size(); i++) {
                if (houseId == guesses.get(i).getId() && guesses.get(i).isCollected()) {
                    //如果在猜你喜欢中找到了应该删除的民宿，并且还标记为“已收藏”
                    //改变标记为"未收藏"，且更新adapter显示
                    guesses.get(i).setCollected(false);
                    if (adapter2 != null && headerView != null) {
                        adapter2.notifyDataSetChanged();
                        swipeRecyclerView.scheduleLayoutAnimation();
                        headerText.setText("以下共" + guesses.size() + "条数据~");
                    }
                    return;
                }
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showGuessList(List<GuessData> guessDataList) {
        guesses = guessDataList;
        if (guessDataList.size() == 0) {
            ToastUtils.toast("没有获取到数据");
            return;
        }

        refreshButton.setVisibility(View.VISIBLE);

        if (swipeRecyclerView.getAdapter() != null) {
//            RecyclerView已经有adapter了，证明这次数据获取不是第一次设置View，而是刷新数据
            adapter2 = new RecommendationsListAdapter(guessDataList, this);
            swipeRecyclerView.setAdapter(adapter2);
            swipeRecyclerView.scheduleLayoutAnimation();

            //收藏成功和一处成功都不需要管 使用EventBus判断是否更新

//            adapter2.setCollectManager(new RecommendationsListAdapter.CollectionManageButtonClickListener() {
//                @Override
//                public void collectSuccessfulCallback(int position, CollectionData collectionsData) {
//                    CollectionsFragment.getCollectionPage().collectionsAddFromGuess(position, collectionsData);
//                }
//
//                @Override
//                public void removeSuccessfulCallback(int houseId) {
//                    CollectionsFragment.getCollectionPage().CollectionsRemoveFromGuess(houseId);
//                }
//            });
            ToastUtils.toast("刷新完成！");
            return;
        }


        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showGuessList();
            }
        });

        adapter2 = new RecommendationsListAdapter(guessDataList, this);
        viewInit(guessDataList, R.drawable.collection_menu_background, No_NEED_MENU,
                null, adapter2, new GridLayoutManager(getContext(), 2));


//        adapter2.setCollectManager(new RecommendationsListAdapter.CollectionManageButtonClickListener() {
//            @Override
//            public void collectSuccessfulCallback(int position, CollectionData collectionsData) {
//                CollectionsFragment.getCollectionPage().collectionsAddFromGuess(position, collectionsData);
//            }
//
//            @Override
//            public void removeSuccessfulCallback(int houseId) {
//                CollectionsFragment.getCollectionPage().CollectionsRemoveFromGuess(houseId);
//            }
//        });

        animationInit(R.anim.collection_recycler_grid_anim);
    }

    private final String No_NEED_MENU = "NONONO";

    /**
     * @param houseId 民宿ID
     * TODO 从猜你喜欢页面点灭了收藏按钮，表示取消收藏，处理将该民宿移出收藏集合和更新显示问题
     * 注意：该方法只在CollectionPage中使用
     */
    @SuppressLint("NotifyDataSetChanged")
    private void CollectionsRemoveFromGuess(int houseId) {
        if (collections != null) {
            for (int i = 0; i < collections.size(); i++) {

                if (houseId == collections.get(i).getId()) {
                    //如果在收藏集合中找到了匹配的民宿
                    collections.remove(i);

                    if (collections.size() == 0) {
                        //如果收藏集合中已全部清空
                        //替换View，显示no Collections
                        ViewGroup parent = (ViewGroup) view.getParent();
                        parent.removeView(view);
                        defaultViewInit(R.drawable.collection_no_collections, "未找到您的收藏，去看看我们的推荐吧！", "点击看我推荐");
                        parent.addView(view);
                    }
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                        swipeRecyclerView.scheduleLayoutAnimation();
                    }
                    if (headerView != null) {
                        headerText.setText("以下共" + collections.size() + "条数据~");
                    }
                    return;
                }
            }
        }
    }

    /**
     * @param position        guess子项在集合中的下标、位置
     * @param collectionsData 新的收藏集合的子项
     *                                                                                                                     TODO 从猜你喜欢中点亮了收藏按钮，表示添加了收藏子项，处理新的CollectionsData在收藏集合中显示的问题
     *                                                                                                                          注意：该方法只在CollectionPage中使用
     */
    @SuppressLint("NotifyDataSetChanged")
    private void collectionsAddFromGuess(int position, CollectionData collectionsData) {
        //如果用户未登录
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            ToastUtils.toast("尚未登录！");
            return;
        }
        if (collections == null) {
            collections = new ArrayList<>();
        }
        //如果开始收藏集合没有元素
        if (collections.size() == 0) {
            //添加收藏至列表
            collections.add(collectionsData);
            //切换View
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
            collectionViewInit();
            parent.addView(view);
        } else {
            //如果收藏集合有元素
            collections.add(collectionsData);
            if (adapter != null && headerView != null) {
                adapter.notifyDataSetChanged();
                swipeRecyclerView.scheduleLayoutAnimation();
                headerText.setText("以下共" + collections.size() + "条数据~");
            }
        }
    }

    /**
     * TODO 当添加收藏、删除收藏成功 调用该方法重新获取收藏列表的数据 更新收藏列表的显示
     */
    @Subscribe
    public void refreshCollectionList(String tag) {
        if (PAGE_COLLECTIONS.equals(dataText) && tag.equals(CollectionManagerService.COLLECTIONS_HAS_CHANGED)) {
            //收藏 接收到收藏列表改动的消息
            presenter.showCollections();
        }
    }

    private void viewInit(@NonNull List<?> dataList, int swipeMenuBackground,
                          String swipeMenuText, OnItemMenuClickListener mItemMenuClickListener,
                          RecyclerView.Adapter mAdapter, GridLayoutManager layoutManager) {
        //dataList在这里一定不为空
        headerText.setText("以下共" + dataList.size() + "条数据~");
        // 创建菜单：
        SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getContext());
                deleteItem.setBackground(swipeMenuBackground)
                        .setText(swipeMenuText)
                        .setTextColor(Color.WHITE)
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                        .setWidth(320);
                rightMenu.addMenuItem(deleteItem);

            }
        };
        //添加菜单
        if (!No_NEED_MENU.equals(swipeMenuText)) {
            //需要菜单 否则不需要添加
            swipeRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        }
        // 菜单点击监听。
        swipeRecyclerView.setOnItemMenuClickListener(mItemMenuClickListener);
        swipeRecyclerView.useDefaultLoadMore();

        swipeRecyclerView.setLayoutManager(layoutManager);
        swipeRecyclerView.setAdapter(mAdapter);
        //添加头尾View
        swipeRecyclerView.addHeaderView(headerView);
        swipeRecyclerView.addFooterView(footerView);
    }

    /**
     * TODO 自定义回调接口，实现Ripple水波纹按钮被点击的回调
     */
    public interface RippleViewClickedListener {
        /**
         * TODO 点击回调
         */
        void onClicked();

    }

    private RippleViewClickedListener mrvcListener;

    public void setRippleViewOnClickListener(RippleViewClickedListener listener) {
        mrvcListener = listener;
    }

    public void scrollToTop() {
        if (swipeRecyclerView != null) {
            swipeRecyclerView.scrollToPosition(0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消EventBus注册订阅
        EventBus.getDefault().unregister(this);
    }
}
