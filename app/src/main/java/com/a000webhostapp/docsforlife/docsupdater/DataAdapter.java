package com.a000webhostapp.docsforlife.docsupdater;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private Context mCtx;

    private List<Data> dataList;
    public Data data;

    public DataAdapter(Context mCtx, List<Data> dataList) {
        this.mCtx = mCtx;
        this.dataList = dataList;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_data, null);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataViewHolder holder, final int position) {

        data = dataList.get(position);
        holder.getAdapterPosition();

        holder.textViewTitle.setText(dataList.get(holder.getAdapterPosition()).getDocname());

        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(dataList.get(holder.getAdapterPosition()).getImage()));

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendIntent(dataList.get(position).getDocname());
            }
        });

        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final String[] items = {"Edit", "Rename", "Delete"};

                final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int itemClicked) {
                        switch (itemClicked) {
                            case 0: {
                                Toast.makeText(mCtx, "Edit Selected", Toast.LENGTH_LONG).show();
                                sendIntent(dataList.get(position).getDocname());
                                break;
                            }
                            case 1: {
                                final AlertDialog.Builder alert = new AlertDialog.Builder(mCtx);
                                alert.setTitle("Rename");
                                //alert.setMessage(dataList.get(position).getDocname());
                                final EditText editText = new EditText(mCtx);
                                final String original_doc_name = dataList.get(position).getDocname();
                                editText.setText(original_doc_name);
                                alert.setView(editText);
                                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String rename_doc_name = editText.getText().toString().trim();
                                        Toast.makeText(mCtx, "Name is " + rename_doc_name, Toast.LENGTH_LONG).show();
                                        DBHelper dbHelper = new DBHelper(mCtx);
                                        dbHelper.onRenameDocName(original_doc_name, rename_doc_name);
                                        Intent intent = new Intent(mCtx, MainActivity.class);
                                        mCtx.startActivity(intent);


                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(mCtx, "No ", Toast.LENGTH_LONG).show();
                                    }
                                });
                                alert.show();
                                break;

                            }
                            case 2: {
                                //Toast.makeText(mCtx, "Delete Selected", Toast.LENGTH_LONG).show();

                                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mCtx);
                                alertBuilder.setTitle("Delete");
                                alertBuilder.setMessage("Once deleted, the doc cannot be recovered. Are you sure you want to delete: " + dataList.get(position).getDocname() + "?");
                                alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DBHelper dbHelper = new DBHelper(mCtx);
                                        int count = dbHelper.onDelete(dataList.get(position).getDocname());
                                        Toast.makeText(mCtx, "Rows Deleted " + count, Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(mCtx, MainActivity.class);
                                        mCtx.startActivity(intent);

                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(mCtx,"Cancel",Toast.LENGTH_LONG).show();
                                    }
                                });
                                alertBuilder.show();
                                break;
                            }
                        }

                    }
                });
                builder.show();
                return true;


            }
        });


    }

    public void sendIntent(String docName) {
        Intent intent = new Intent(mCtx, DisplayActivity.class);
        intent.putExtra("NAME", docName);
        mCtx.startActivity(intent);

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class DataViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        ImageView imageView;

        public DataViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textView_Data);
            imageView = itemView.findViewById(R.id.imageView);


        }


    }


}
