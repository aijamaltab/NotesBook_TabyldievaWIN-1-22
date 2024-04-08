package com.example.notesbook_tabyldievawin_1_22.ui.dashboard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.Room;

import com.example.notesbook_tabyldievawin_1_22.MainActivity;
import com.example.notesbook_tabyldievawin_1_22.R;
import com.example.notesbook_tabyldievawin_1_22.databinding.FragmentInputBinding;
import com.example.notesbook_tabyldievawin_1_22.models.Student;
import com.example.notesbook_tabyldievawin_1_22.room.AppDatabase;
import com.example.notesbook_tabyldievawin_1_22.room.StudentDao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class InputFragment extends Fragment {

    private FragmentInputBinding binding;
    private AppDatabase appDatabase;
    private StudentDao studentDao;
    private ActivityResultLauncher<String> content_l;
    private Bitmap bitmap_imageStudent;

    private boolean isImgSelected = false;

    NavController navController;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInputBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnLoadPhoto.setOnClickListener(v1 -> {

            InputFragment.this.content_l.launch("image/*");
        });
        content_l = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        try {
                            bitmap_imageStudent = MediaStore
                                    .Images
                                    .Media
                                    .getBitmap(getContext()
                                            .getContentResolver(), result);
                            binding.imageInput.setImageBitmap(bitmap_imageStudent);
                            isImgSelected = true;
                        } catch (IOException error) {
                            error.printStackTrace();
                            isImgSelected = false;
                        }
                    }

                });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnSaveInput.setOnClickListener(v2 ->{

            String nameSurnameStudent = binding.nameSurnameInput.getText().toString();
            String telStudent = binding.numberInput.getText().toString();

            if (nameSurnameStudent.isEmpty() || telStudent.isEmpty()) {

                Toast.makeText(requireActivity(), "Заполните поля ИМЯ КОНТАКТА",
                        Toast.LENGTH_SHORT).show();
                isImgSelected=false;

            } else {
                if(isImgSelected){

                    ByteArrayOutputStream baos_imageStudent = new ByteArrayOutputStream();
                    bitmap_imageStudent.compress((Bitmap.CompressFormat.PNG), 100, baos_imageStudent);

                    byte[] imageStudent = baos_imageStudent.toByteArray();

                    Student student = new Student(
                        nameSurnameStudent, telStudent, imageStudent);


                    this.appDatabase = Room.databaseBuilder(
                            binding.getRoot().getContext(),
                            AppDatabase.class, "database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();

                    studentDao = appDatabase.studentDao();

                    studentDao.insert(student);

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
                    navController.navigate(R.id.nav_view);

                    } else {
                        Toast.makeText(requireActivity(), "Загрузить фото",
                                Toast.LENGTH_SHORT).show();
                    }

                }

        });
        binding.btnPerehod.setOnClickListener(v3 -> {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
            navController.navigate(R.id.action_navigation_input_to_navigation_all_friends);

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}