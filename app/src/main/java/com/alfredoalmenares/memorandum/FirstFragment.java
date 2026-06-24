package com.alfredoalmenares.memorandum;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfredoalmenares.memorandum.databinding.FragmentFirstBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// List memorandum items
public class FirstFragment extends Fragment implements IRemover {

    private FragmentFirstBinding binding;
    ArrayList<CustomTask> content;
    TaskData taskData;
    private boolean isEmpty;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Set title
        if(getActivity()!=null)
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Memorandum");

        binding = FragmentFirstBinding.inflate(inflater, container, false);

        // Get file data in order to call the adapter with all needed tasks

        taskData = new TaskData(getContext());
        content = taskData.getAllTasks();

        // Call adapter using file data
        if(content.isEmpty()){
            binding.listview.setAdapter(new MyListAdapter(container.getContext(),
                    new ArrayList<>(List.of(new CustomTask(0, "No task yet")))
                    ,this, true));
            isEmpty=true;
        }
        else{
            binding.listview.setAdapter(new MyListAdapter(container.getContext(), content,this, false));
            isEmpty=false;
        }


        binding.listview.setOnItemClickListener((adapterView, view, i, l) -> {
            if(isEmpty){
                // Change title
                ((MainActivity)getActivity()).getSupportActionBar().setTitle("Add Task");
                // Navigate to Add Fragment
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_AddFragment);
            }else{
                // Change title
                ((MainActivity)getActivity()).getSupportActionBar().setTitle("Edit Task");

                // Edit
                Bundle bundle = new Bundle();
                bundle.putInt(TaskData.TASK_ID, content.get(i).getId());
                bundle.putString(TaskData.TASK_DESCRIPTION, content.get(i).getDesc());
                bundle.putString(TaskData.TASK_TITLE, content.get(i).getTitle());
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_EditFragment, bundle);


            }
        });

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(view1 -> {
            // Change title
            ((MainActivity)getActivity()).getSupportActionBar().setTitle("Add Task");

            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_AddFragment);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void remover(int i){
        taskData.removeOneTask(i);
        content=taskData.getAllTasks();
        if(getContext()!=null)
            binding.listview.setAdapter(new MyListAdapter(getContext(),
                    content.isEmpty() ? new ArrayList<>(List.of(new CustomTask(0, "No task yet"))) : content,
                    this,
                    content.isEmpty()));
    }

}