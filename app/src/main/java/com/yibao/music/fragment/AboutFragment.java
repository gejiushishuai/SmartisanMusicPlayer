package com.yibao.music.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yibao.music.R;
import com.yibao.music.base.BaseFragment;
import com.yibao.music.fragment.dialogfrag.RelaxDialogFragment;
import com.yibao.music.fragment.dialogfrag.TopBigPicDialogFragment;
import com.yibao.music.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @项目名： ArtisanMusic
 * @包名： com.yibao.music.folder
 * @文件名: AboutFragment
 * @author: Stran
 * @Email: www.strangermy@outlook.com / www.strangermy98@gmail.com
 * @创建时间: 2018/2/9 20:51
 * @描述： {TODO}
 */

public class AboutFragment extends BaseFragment {


    @BindView(R.id.about_header_iv)
    CircleImageView mAboutHeaderIv;
    private Unbinder unbinder;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        initListener();
        return view;
    }

    private void initListener() {
        mAboutHeaderIv.setOnClickListener(v -> RelaxDialogFragment.newInstance().show(getFragmentManager(), "girlsDialog"));
        mAboutHeaderIv.setOnLongClickListener(view -> {
            TopBigPicDialogFragment.newInstance("")
                    .show(getFragmentManager(), "album");
            return true;
        });
    }

    public static AboutFragment newInstance() {

        return new AboutFragment();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean backPressed() {
        return false;
    }
}
