package com.alfredoalmenares.memorandum;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.alfredoalmenares.memorandum.databinding.EditFragmentBinding;

public class EditFragment extends Fragment {
    private EditFragmentBinding binding;
    private String desc;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = EditFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TaskData taskData = new TaskData(getContext());
        desc = getArguments().getString(TaskData.TASK_DESCRIPTION);
        binding.edittextDescription.setText(desc);

        binding.buttonSubmit.setOnClickListener(view1 -> {
            // Edit task
            int id = getArguments().getInt(TaskData.TASK_ID);

            // Get desc
            desc = binding.edittextDescription.getText().toString();
            if(desc.isEmpty()) return;
            //Bundle bundle = new Bundle();
            //bundle.putString(TaskData.TASK_DESCRIPTION, desc);
            taskData.updateTask(id, desc);

            NavHostFragment.findNavController(EditFragment.this)
                    .navigate(R.id.action_EditFragment_to_FirstFragment);
        });
    }
}
