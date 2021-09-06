package com.example.applicationforstudents.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.applicationforstudents.BuildConfig;
import com.example.applicationforstudents.R;
import com.example.applicationforstudents.Architecture.ViewModelMy;
import com.example.applicationforstudents.Room.Subject;
import com.example.applicationforstudents.SubjectsAdapter;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FragmentList extends Fragment {
    Calendar calendar = Calendar.getInstance();
    Button buttonAddElement;
    ImageButton datePicker;
    HorizontalPicker horizontalPicker;
    CustomBottomSheet customBottomSheet;
    ViewModelMy myModel;
    TextView textNameOfDay;
    ListView listViewOfDay;
    String fullDate;
    List<Subject> subjectList = new LinkedList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Підключення ViewModel
        myModel = new ViewModelProvider(this).get(ViewModelMy.class);
        myModel.getLiveData().observe(getViewLifecycleOwner(),observerViewModel);
        /*
        if(savedInstanceState == null)
            calendar = Calendar.getInstance();
        else
            calendar = myModel.getLiveData().getValue();
            */

        //Ініціалізація елементів
        textNameOfDay = view.findViewById(R.id.textNameOfDay);

        datePicker = view.findViewById(R.id.imageButtonCalendar);
        datePicker.setOnClickListener(openDatePicker);

        buttonAddElement = view.findViewById(R.id.buttonAddElement);
        buttonAddElement.setOnClickListener(addElement);

        listViewOfDay = view.findViewById(R.id.listViewOfDay);
        listViewOfDay.setOnItemClickListener(clickListListener);

        horizontalPicker = (HorizontalPicker) view.findViewById(R.id.datePicker);
        createHorizontalPicker();
    }

    //Слухач для ViewModel
    Observer<List<Subject>> observerViewModel = new Observer<List<Subject>>() {
        @Override
        public void onChanged(List<Subject> subjects) {
            Log.d("MyLog", "+++ Change Live DAta");
            subjectList = subjects;
            resetListView();
        }
    };

    //Слухач кліку на ListView
    AdapterView.OnItemClickListener clickListListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String selected = ((TextView) view.findViewById(R.id.textViewId)).getText().toString();
            Log.d("MyLog", "id List = " + id + " pos " + position + " " + selected);
            startBottomSheet(Long.valueOf(selected));
        }
    };

    //Обновлення списку предметів на день
    public void resetListView() {
        Date date = new Date();
        date.setTime(calendar.getTimeInMillis());
        fullDate = new SimpleDateFormat("yyyy.MM.dd").format(date);
        Log.d("MyLog", "Fulldate" + fullDate);

        List<Subject> changeList = new LinkedList<>();
        for(Subject el:subjectList){
            if(el.getDate().equals(fullDate)){
                changeList.add(el);
            }
        }

        SubjectsAdapter adapter = new SubjectsAdapter(getContext(), R.layout.item_subject, changeList);
        listViewOfDay.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listViewOfDay);

        String dayWeekText = new SimpleDateFormat("EEEE").format(date);
        textNameOfDay.setText(dayWeekText.substring(0, 1).toUpperCase() + dayWeekText.substring(1));

        Log.d("MyLog", "================" + calendar.getTime() + " " + subjectList.size());
    }

    //Підбирання висоти ListView,щоб всі елементи поміщались
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);

           // if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        ViewGroup.LayoutParams.MATCH_PARENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
            Log.d("MyLog","TOTAL: " + totalHeight);
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = (int) (totalHeight + ((listView.getDividerHeight())) * (listAdapter.getCount()-1));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    //Слухач ImageButton для відкриття календаря
    View.OnClickListener openDatePicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new DatePickerDialog(getContext(), dateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    };

    //Слухач DatePickerDialog для зміни данних в ViewModel
    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            resetListView();
            Log.d("MyLog", calendar.getTime().toString());
        }
    };


    //Створення і редагування горизонтального календаря
    public void createHorizontalPicker() {
        horizontalPicker.setListener(datePickerListener)
                .setDays(60)
                .setOffset(30)
                .setDateSelectedColor(getResources().getColor(R.color.transparent))
                .setDateSelectedTextColor(getResources().getColor(R.color.tabCheck))
                .setMonthAndYearTextColor(getResources().getColor(R.color.transparent))
                .setTodayDateTextColor(getResources().getColor(R.color.black))
                .setTodayDateBackgroundColor(getResources().getColor(R.color.transparent))
                .setDayOfWeekTextColor(getResources().getColor(R.color.textGrey))
                .setUnselectedDayTextColor(getResources().getColor(R.color.textGrey))
                .showTodayButton(false)
                .init();
        horizontalPicker.setBackgroundColor(getResources().getColor(R.color.white));

        DateTime dateTime = new DateTime(calendar.getTime());
        //Log.d("MyLog","dateToday   Year:"+ dateTime.getYearOfCentury() + " month:" + dateTime.getMonthOfYear() + " day" + dateTime.getDayOfMonth());
        horizontalPicker.setDate(dateTime);
    }

    //Слухач зміни дати для горизонтального календаря
    DatePickerListener datePickerListener = new DatePickerListener() {
        @Override
        public void onDateSelected(DateTime dateSelected) {
            calendar.set(Calendar.YEAR, dateSelected.getYear());
            calendar.set(Calendar.MONTH, dateSelected.getMonthOfYear() - 1);
            calendar.set(Calendar.DAY_OF_MONTH, dateSelected.getDayOfMonth());
            resetListView();
        }
    };


    //Слухач Button для відкриття BottomSheet
    View.OnClickListener addElement = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startBottomSheet(-1);
        }
    };

    public void startBottomSheet(long id) {
        Date date = new Date();
        customBottomSheet = new CustomBottomSheet();
        date.setTime(calendar.getTimeInMillis());
        String dayWithMonth = new SimpleDateFormat("EEEE, d MMMM").format(date), fullDate = new SimpleDateFormat("yyyy.MM.dd").format(date);

        Log.d("MyLog", "StartBottomSheet ==== " + calendar.getTime());
        Bundle bundle = new Bundle();
        bundle.putString("fullDate", fullDate);
        bundle.putString("dayWithMonth", dayWithMonth.substring(0, 1).toUpperCase() + dayWithMonth.substring(1));
        bundle.putLong("id", id);
        customBottomSheet.setArguments(bundle);
        customBottomSheet.show(getActivity().getSupportFragmentManager(), "");
    }
}
