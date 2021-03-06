package com.yibao.music.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yibao.music.MusicApplication;
import com.yibao.music.base.listener.OnBackHandlePressedListener;
import com.yibao.music.base.listener.OnMusicItemClickListener;
import com.yibao.music.model.AlbumInfo;
import com.yibao.music.model.ArtistInfo;
import com.yibao.music.model.MusicBean;
import com.yibao.music.model.greendao.MusicBeanDao;
import com.yibao.music.model.greendao.MusicInfoDao;
import com.yibao.music.util.MusicListUtil;
import com.yibao.music.util.RandomUtil;
import com.yibao.music.util.RxBus;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Stran
 * @项目名： BigGirl
 * @包名： com.yibao.biggirl.base
 * @文件名: BaseFragment
 * @创建时间: 2018/1/1 17:36
 * @描述： TODO
 */
public abstract class BaseFragment extends Fragment {
    protected String tag;
    protected Activity mActivity;
    protected static List<AlbumInfo> mAlbumList;
    protected static List<ArtistInfo> mArtistList;
    protected RxBus mBus;
    protected CompositeDisposable compositeDisposable;
    public static List<MusicBean> mSongList;
    public MusicBeanDao mMusicBeanDao;
    public MusicInfoDao mMusicInfoDao;
    protected boolean isShowDetailsView = false;
    private OnBackHandlePressedListener mHandlePressedListener;
    public List<MusicBean> mMusicAddtimeList;

    protected BaseFragment() {
        mMusicBeanDao = MusicApplication.getIntstance().getMusicDao();
        mMusicInfoDao = MusicApplication.getIntstance().getMusicInfoDao();
        mSongList = mMusicBeanDao.queryBuilder().list();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        tag = this.getClass().getSimpleName();
        mActivity = getActivity();
        compositeDisposable = new CompositeDisposable();
        mBus = MusicApplication.getIntstance().bus();
        if (!(getActivity() instanceof OnBackHandlePressedListener)) {
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        } else {
            this.mHandlePressedListener = (OnBackHandlePressedListener) getActivity();
        }

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mAlbumList == null) {
            mAlbumList = MusicListUtil.getAlbumList(mSongList);
        }
        if (mMusicAddtimeList == null) {
            mMusicAddtimeList = MusicListUtil.sortMusicAddtime(mMusicBeanDao.queryBuilder().list());
        }
        if (mArtistList == null) {
            mArtistList = MusicListUtil.getArtistList(mSongList);

        }

    }

    protected void randomPlayMusic() {
        int position = RandomUtil.getRandomPostion(mSongList);
        if (getActivity() instanceof OnMusicItemClickListener) {
            ((OnMusicItemClickListener) getActivity()).startMusicService(position);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mHandlePressedListener.putFragment(this);
    }

    /**
     * 返回子类的一个标记
     *
     * @return
     */
    public abstract boolean backPressed();

    @Override
    public void onPause() {
        super.onPause();
        compositeDisposable.dispose();
    }


}
