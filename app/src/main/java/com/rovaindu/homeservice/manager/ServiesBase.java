package com.rovaindu.homeservice.manager;



import com.rovaindu.homeservice.retrofit.models.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiesBase {

    private List<Service> mOrders;
    private static volatile ServiesBase instance = new ServiesBase();
    private ServiesBase(){
        mOrders = new ArrayList<>();
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }
    private ServiesBase(Service products)
    {
        mOrders.add(products);
    }

    public List<Service> getmOrders() {
        return mOrders;
    }

    public boolean AssignOrder(List<Service> products) {

        this.mOrders.addAll(products);
        return true;
        /*
        if (mOrders.size() > 0) {
            if (this.mOrders.get(products.getId() - 1).getId() == (products.getId())) {
                return false;
            } else {

                this.mOrders.add(products.getId(), products);
                return true;
            }
        }
        else
        {

            this.mOrders.add(products.getId(), products);
            return true;
        }

         */

    }
    public boolean InsertOrder(Service products) {

        if (mOrders.size() > 0) {
            for (int i = 0 ; i < mOrders.size(); i++) {
                if(mOrders.get(i).getId() == products.getId())
                {
                    return false;

                }
                else
                {
                    this.mOrders.add(products);
                    return true;
                }
            }
            return false;
            /*
            if (this.mOrders.get(products.getId() - 1).getId() == (products.getId())) {// - 1
                return false;
            } else {
                //this.mOrders.add(products.getId(), products);
                this.mOrders.add(products);
                return true;
            }

             */
        }
        else
        {
            this.mOrders.add(products);
            //this.mOrders.add(products.getId(), products);
            return true;
        }

    }
    public boolean CheckAvaliablty(int product_id) {

        if (mOrders.size() > 0) {

            for (int i = 0; i < mOrders.size(); i++) {
                if(mOrders.get(i).getId() == product_id)
                {
                    return true;
                }


            }
            return false;

        }
        else {
            return false;
        }

    }

    public boolean RemoveOrder(Service products) {

        if (mOrders.size() > 0) {


            for (int i = 0 ; i < mOrders.size(); i++) {
                if(mOrders.get(i).getId() == products.getId())
                {
                    mOrders.remove(mOrders.get(i));
                    return true;

                }
            }
            return false;

        }
        else
        {
            return false;
        }

    }

    public static ServiesBase getInstance(){
        if(instance == null)
        {
            instance = new ServiesBase();
        }
        return instance;
    }

    public void dispose() {
        clearTempUser();
    }

    private void clearTempUser() {
        mOrders.clear();

    }

}
