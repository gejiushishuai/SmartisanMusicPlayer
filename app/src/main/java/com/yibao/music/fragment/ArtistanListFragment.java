package com.yibao.music.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yibao.music.R;
import com.yibao.music.adapter.ArtistAdapter;
import com.yibao.music.adapter.DetailsListAdapter;
import com.yibao.music.base.BaseFragment;
import com.yibao.music.model.ArtistInfo;
import com.yibao.music.model.MusicBean;
import com.yibao.music.model.greendao.MusicBeanDao;
import com.yibao.music.util.Constants;
import com.yibao.music.util.LogUtil;
import com.yibao.music.view.music.DetailsView;
import com.yibao.music.view.music.MusicView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @项目名： ArtisanMusic
 * @包名： com.yibao.music.artisanlist
 * @文件名: ArtistanListFragment
 * @author: Stran
 * @Email: www.strangermy@outlook.com / www.stranger98@gmail.com
 * @创建时间: 2018/2/4 23:49
 * @描述： {TODO}
 */

public class ArtistanListFragment extends BaseFragment {

    @BindView(R.id.artist_music_view)
    MusicView mMusicView;
    @BindView(R.id.details_view)
    DetailsView mDetailsView;
    private Unbinder unbinder;
    private ArtistAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.artisan_list_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        initData();
        initListener();
        return view;
    }


    private void initListener() {
        mAdapter.setItemListener(ArtistanListFragment.this::openDetailsView);
    }

    private void initData() {
        mAdapter = new ArtistAdapter(mArtistList);
        mMusicView.setAdapter(getActivity(), Constants.NUMBER_TWO, true, mAdapter);


    }

    private void openDetailsView(ArtistInfo bean) {
        if (isShowDetailsView) {
            mDetailsView.setVisibility(View.INVISIBLE);
            mMusicView.setVisibility(View.VISIBLE);

        } else {
            mMusicView.setVisibility(View.INVISIBLE);
            mDetailsView.setVisibility(View.VISIBLE);
            List<MusicBean> list = mMusicBeanDao.queryBuilder().where(MusicBeanDao.Properties.Artist.eq(bean.getArtist())).build().list();
            DetailsListAdapter adapter = new DetailsListAdapter(getActivity(), list, Constants.NUMBER_ONE);

            mDetailsView.setAdapter(getActivity(), Constants.NUMBER_ONE, bean, adapter);

        }
        isShowDetailsView = !isShowDetailsView;
    }

    public static ArtistanListFragment newInstance() {

        return new ArtistanListFragment();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean backPressed() {
        LogUtil.d("========== 自己处理返回事件");

        return mDetailsView.getVisibility() != View.GONE;
    }
}
