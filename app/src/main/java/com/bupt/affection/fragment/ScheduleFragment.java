package com.bupt.affection.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.bupt.affection.R;
import com.bupt.affection.common.CommonUtil;
import com.bupt.affection.common.PreferencesUtil;
import com.bupt.affection.common.UserConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView food_1, food_2, food_3, food_4, food_5;
    private TextView act_1, act_2, act_3;
    private TextView sleep_1, sleep_2, sleep_3;
    private TextView fit_1, fit_2, fit_3;
    private String foods[];
    private String acts[];
    private String sleeps[];
    private List<TextView> foodTVList, actTVList,sleepTVList,fitTVList;
//    private SwipeRefreshLayout refresh;


    private OnFragmentInteractionListener mListener;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
//        TextView textView_meal = (TextView) view.findViewById(R.id.meal);
//        textView_meal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
//                startActivity(intent);
//            }
//        });
//        TextView textView_entertainment = (TextView) view.findViewById(R.id.entertainment);
//        textView_entertainment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
//                startActivity(intent);
//            }
//        });
//        TextView textView_sleep = (TextView) view.findViewById(R.id.sleep);
//        textView_sleep.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
//                startActivity(intent);
//            }
//        });
//        TextView textView_health = (TextView) view.findViewById(R.id.health);
//        textView_health.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
//                startActivity(intent);
//            }
//        });
        foodTVList = new ArrayList<>();
        actTVList = new ArrayList<>();
        sleepTVList = new ArrayList<>();
        fitTVList = new ArrayList<>();

        food_1 = (TextView) view.findViewById(R.id.food_1);
        food_2 = (TextView) view.findViewById(R.id.food_2);
        food_3 = (TextView) view.findViewById(R.id.food_3);
        food_4 = (TextView) view.findViewById(R.id.food_4);
        food_5 = (TextView) view.findViewById(R.id.food_5);

        act_1 = (TextView) view.findViewById(R.id.fun_1);
        act_2 = (TextView) view.findViewById(R.id.fun_2);
        act_3 = (TextView) view.findViewById(R.id.fun_3);

        sleep_1 = (TextView) view.findViewById(R.id.sleep_1);
        sleep_2 = (TextView) view.findViewById(R.id.sleep_2);
        sleep_3 = (TextView) view.findViewById(R.id.sleep_3);

        fit_1 = (TextView) view.findViewById(R.id.fit_1);
        fit_2 = (TextView) view.findViewById(R.id.fit_2);
        fit_3 = (TextView) view.findViewById(R.id.fit_3);

        foodTVList.add(food_1);
        foodTVList.add(food_2);
        foodTVList.add(food_3);
        foodTVList.add(food_4);
        foodTVList.add(food_5);

        actTVList.add(act_1);
        actTVList.add(act_2);
        actTVList.add(act_3);

        sleepTVList.add(sleep_1);
        sleepTVList.add(sleep_2);
        sleepTVList.add(sleep_3);

        fitTVList.add(fit_1);
        fitTVList.add(fit_2);
        fitTVList.add(fit_3);

//        refresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
//        refresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
//        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getDataFromLeanCloud();
//            }
//        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataFromLeanCloud();
    }

    private void getDataFromLeanCloud() {
        //登录时，拉取对应老人的信息
        if (CommonUtil.loginStatus(getActivity()) && null != PreferencesUtil.getString(getActivity(), UserConfig.PRENTID)) {
            //登录时查看指定未登录数据
            String id = PreferencesUtil.getString(getActivity(), UserConfig.PRENTID);
//            Logger.d(id);
            AVQuery<AVObject> avQuery = new AVQuery<>("Parents");
            avQuery.getInBackground(id, new GetCallback<AVObject>() {
                @Override
                public void done(AVObject avObject, AVException e) {
                    List<String> foodList = new ArrayList<>();
                    List<String> actList = new ArrayList<>();
                    List<String> sleepList = new ArrayList<>();
                    List<String> fitList = new ArrayList<>();

                    foodList = (List<String>) avObject.get("food");
                    actList = (List<String>) avObject.get("act");
                    sleepList = (List<String>) avObject.get("sleep");
                    fitList = (List<String>) avObject.get("fit");

                    for (int i = 0;i<foodTVList.size();i++) {
                        if (i<foodList.size()){
                            foodTVList.get(i).setText(foodList.get(i));
                        }

                    }

                    for (int i = 0;i<actTVList.size();i++) {
                        if (i<actList.size()){
                            actTVList.get(i).setText(actList.get(i));
                        }

                    }
                    for (int i = 0;i<sleepTVList.size();i++) {
                        if (i<sleepList.size()){
                            sleepTVList.get(i).setText(sleepList.get(i));
                        }

                    }

                    for (int i = 0;i<fitTVList.size();i++) {
                        if (i<fitList.size()){
                            fitTVList.get(i).setText(fitList.get(i));
                        }

                    }

//                    if (refresh.isRefreshing()){
//                        refresh.setRefreshing(false);
//                    }
                    String longitude = avObject.get("longitude").toString();
                    String latitude = avObject.get("latitude").toString();
//                    PreferencesUtil.putString(getActivity(),UserConfig.longitude,longitude);
//                    PreferencesUtil.putString(getActivity(),UserConfig.longitude,longitude);
                }
            });

//            avQuery.findInBackground(new FindCallback<AVObject>() {
//                @Override
//                public void done(List<AVObject> list, AVException e) {
////                    ArrayList<AVObject> schedules = (ArrayList<AVObject>) list;
//                    for (AVObject schedule:list){
//                        String mobile = PreferencesUtil.getString(getActivity(), UserConfig.MOBILE);
//                        if (schedule.get("children")!=null && schedule.get("children").equals(mobile));
//                        {
////                            Logger.json(schedule.toString());
//                            String id = schedule.getObjectId();
//                            PreferencesUtil.putString(getActivity(),"parentId",id);
//                            Logger.d(id);
//                        }
//                    }
//                }
//            });
        } else {
            //未登录时查看指定未登录数据
            AVQuery<AVObject> avQuery = new AVQuery<>("ScheduleWithoutLogin");
            avQuery.getInBackground("579f83ae0a2b580058162ece", new GetCallback<AVObject>() {
                @Override
                public void done(AVObject avObject, AVException e) {
                    Map<String, String> foodMap = new HashMap<String, String>();
                    Map<String, String> actMap = new HashMap<String, String>();
                    Map<String, String> sleepMap = new HashMap<String, String>();

                    foodMap = (Map<String, String>) avObject.get("food");
                    String food1 = foodMap.get("food_1").toString();
                    String food2 = foodMap.get("food_2").toString();
                    String food3 = foodMap.get("food_3").toString();

                    actMap = (Map<String, String>) avObject.get("act");
                    String act1 = actMap.get("act_1").toString();
                    String act2 = actMap.get("act_2").toString();
                    String act3 = actMap.get("act_3").toString();

                    sleepMap = (Map<String, String>) avObject.get("sleep");
                    String sleep1 = sleepMap.get("sleep_1").toString();
                    String sleep2 = sleepMap.get("sleep_2").toString();
                    String sleep3 = sleepMap.get("sleep_3").toString();


                    food_1.setText(food1);
                    food_2.setText(food2);
                    food_3.setText(food3);

                    act_1.setText(act1);
                    act_2.setText(act2);
                    act_3.setText(act3);

                    sleep_1.setText(sleep1);
                    sleep_2.setText(sleep2);
                    sleep_3.setText(sleep3);
//                    if (refresh.isRefreshing()){
//                        refresh.setRefreshing(false);
//                    }
                }
            });
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
