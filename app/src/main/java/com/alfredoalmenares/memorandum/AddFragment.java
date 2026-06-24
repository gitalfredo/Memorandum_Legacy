package com.alfredoalmenares.memorandum;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfredoalmenares.memorandum.databinding.AddfragmentBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class AddFragment extends Fragment {
    private AddfragmentBinding binding;
    private String desc;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = AddfragmentBinding.inflate(inflater, container, false);
        // Get file data in order to call the adapter with all needed tasks
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TaskData taskData = new TaskData(getContext());
        desc="";

        binding.edittextDescription.setText(desc);
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get desc
                desc = binding.edittextDescription.getText().toString();
                if(desc.isEmpty()) return;
                //Bundle bundle = new Bundle();
                //bundle.putString(TaskData.TASK_DESCRIPTION, desc);
                // Add task
                taskData.addOne(desc);


                NavHostFragment.findNavController(AddFragment.this)
                        .navigate(R.id.action_AddFragment_to_FirstFragment);
            }
        });
    }
}
