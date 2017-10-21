package com.example.roman.testatlanteam;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roman.testatlanteam.model.Comments;
import com.example.roman.testatlanteam.model.Posts;
import com.example.roman.testatlanteam.model.Todos;
import com.example.roman.testatlanteam.model.Users;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class CardFragment extends Fragment implements View.OnClickListener {

    Button btnPost, btnComments, btnTodos;
    EditText editTextPost, editTextComments;
    TextView textViewPost,textViewComments, textViewTodos, textViewUsers;
    ImageView image;
    final String BASE_URL = "https://jsonplaceholder.typicode.com";
    RetrofitInterface service;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.card_fragment, null);
        btnPost = v.findViewById(R.id.btnPost);
        btnComments = v.findViewById(R.id.btnComments);
        btnTodos = v.findViewById(R.id.btnTodos);
        editTextPost = v.findViewById(R.id.editTextPost);
        editTextComments = v.findViewById(R.id.editTextComments);
        textViewPost = v.findViewById(R.id.textViewPost);
        textViewComments = v.findViewById(R.id.textViewComments);
        textViewTodos = v.findViewById(R.id.textViewTodos);
        textViewUsers = v.findViewById(R.id.textViewUsers);
        image = v.findViewById(R.id.imageView);

        btnPost.setOnClickListener(this);
        btnComments.setOnClickListener(this);
        btnTodos.setOnClickListener(this);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(RetrofitInterface.class);

        userRequest();
        Picasso.with(getActivity()).load("http://placehold.it/600/24f355").into(image);

        return v;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPost:
                postRequest();
                break;

            case R.id.btnComments:
                commentsRequest();
                break;

            case R.id.btnTodos:
                todosRequest();
                break;

        }


    }
    void postRequest(){
        if (editTextPost.getText().toString().equals("") ) {
            return;
        }
        int PostId = Integer.parseInt(editTextPost.getText().toString());
        if(PostId < 1 | PostId > 100 | editTextPost.getText() == null){
            Toast.makeText(getActivity(), "Id должно быть от 1 до 100",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Call<Posts> call = service.getPosts(Integer.toString(PostId));
        call.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {

                Posts posts = response.body();
                textViewPost.setText("UserId: "+ posts.getUserId()+"\n" +
                        "Id: "+ posts.getId() + "\n" + "Title: " + posts.getTitle() +
                        "\n"+ "Body: " + posts.getBody());
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {

            }
        });

    }

    void commentsRequest(){
        if (editTextPost.getText().toString().equals("")) {
            return;
        }
        int CommentsId = Integer.parseInt(editTextComments.getText().toString());
        if(CommentsId < 1 | CommentsId > 500 ){
            Toast.makeText(getActivity(), "№ Комментария должен быть от 1 до 500",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Call<Comments> callComments = service.getComments(Integer.toString(CommentsId));
        callComments.enqueue(new Callback<Comments>() {
            @Override
            public void onResponse(Call<Comments> call, Response<Comments> response) {

                Comments comments = response.body();
                textViewComments.setText("PostId: "+ comments.getPostId() + "\n" +
                        "Id: "+ comments.getId() + "\n" + "Name: " +
                        comments.getName() + "\n"+ "Email: " + comments.getEmail() +
                        "\n"+ "Body: " + comments.getBody());
            }

            @Override
            public void onFailure(Call<Comments> call, Throwable t) {

            }
        });
    }

    void userRequest(){
        Call<List<Users>> callTodoso = service.getUsers();
        callTodoso.enqueue(new Callback<List<Users>> () {
            @Override
            public void onResponse(Call<List<Users>>  call, Response<List<Users>>  response) {

                List<Users> listUser = response.body();
                for (int n = 0; n < 5; n++) {
                    textViewUsers.append("Name: " + listUser.get(n).getName() + "\n");
                }
            }

            @Override
            public void onFailure(Call<List<Users>>  call, Throwable t) {

            }
        });
    }

    void todosRequest(){
        int randomTodos = 1 + (int) (Math.random() * 200);
        Call<Todos> callTodoso = service.getTodos(Integer.toString(randomTodos));
        callTodoso.enqueue(new Callback<Todos>() {
            @Override
            public void onResponse(Call<Todos> call, Response<Todos> response) {

                Todos todos = response.body();
                textViewTodos.setText("UserId: "+ todos.getUserId() + "\n" +
                        "Id: "+ todos.getId() + "\n" + "Title: " +
                        todos.getTitle() + "\n"+ "Completed: " + todos.getCompleted());
            }

            @Override
            public void onFailure(Call<Todos> call, Throwable t) {

            }
        });
    }
}

