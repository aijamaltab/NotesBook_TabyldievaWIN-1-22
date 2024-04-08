package com.example.notesbook_tabyldievawin_1_22.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomSQLiteQuery;

import com.example.notesbook_tabyldievawin_1_22.databinding.FragmentAllFriendsBinding;
import com.example.notesbook_tabyldievawin_1_22.room.AppDatabase;
import com.example.notesbook_tabyldievawin_1_22.room.StudentDao;
import com.example.notesbook_tabyldievawin_1_22.ui.dashboard.StudentAdapter;

public class AllFriendsFragment extends Fragment {

    private FragmentAllFriendsBinding binding;
    private AppDatabase appDatabase;
    private StudentDao studentDao;
    private StudentAdapter studentAdapter;
    private NavController navController;

    RecyclerView rv_main_catalog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAllFriendsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rv_main_catalog = binding.rvMain;
        studentAdapter = new StudentAdapter();
        rv_main_catalog.setAdapter(studentAdapter);

        appDatabase = Room.databaseBuilder(binding.getRoot().getContext()
                        ,AppDatabase.class, "database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        studentDao = appDatabase.studentDao();
        studentAdapter.setList(studentDao.getAll());
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}