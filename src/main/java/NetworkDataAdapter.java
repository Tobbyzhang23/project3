import com.google.gson.Gson;

public class NetworkDataAdapter implements IDataAdapter {

    String host = "localhost";
    int port = 1024;

    Gson gson = new Gson();
    SocketNetworkAdapter adapter = new SocketNetworkAdapter();
    MessageModel msg = new MessageModel();

    @Override
    public int connect(String dbfile) {
        int pos = dbfile.indexOf(":");
        host = dbfile.substring(0, pos);
        port = Integer.parseInt(dbfile.substring(pos+1, dbfile.length()));
        return 0;
    }

    @Override
    public int disconnect() {
        return 0;
    }

    @Override
    public ProductModel loadProduct(int id) {
        msg.code = MessageModel.GET_PRODUCT;
        msg.data = Integer.toString(id);
        try {
            msg = adapter.exchange(msg, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return null;
        else {
            return gson.fromJson(msg.data, ProductModel.class);
        }
    }

    @Override
    public int saveProduct(ProductModel model) {
        msg.code = MessageModel.PUT_PRODUCT;
        msg.data = gson.toJson(model);

        try {
            msg = adapter.exchange(msg, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return IDataAdapter.PRODUCT_SAVE_FAILED;
        else {
            return IDataAdapter.PRODUCT_SAVE_OK;
        }
    }

    @Override
    public ProductListModel searchProduct(String name, double minPrice, double maxPrice) {
        msg.code = MessageModel.SEARCH_PRODUCT;
        msg.data = "name";

        try {
            msg = adapter.exchange(msg, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return null;
        else {

            return gson.fromJson(msg.data, ProductListModel.class);
        }
    }

    @Override
    public CustomerModel loadCustomer(int id) {
        return null;
    }

    @Override
    public int saveCustomer(CustomerModel customer) {
        return 1;
    }

    @Override
    public PurchaseModel loadPurchase(int purchaseID) {
        return null;
    }

    @Override
    public int savePurchase(PurchaseModel model) {
        return 0;
    }

    @Override
    public UserModel loadUser(String username) {
        msg.code = MessageModel.GET_USER;
        msg.data = username;
        try {
            msg = adapter.exchange(msg, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return null;
        else {
            return gson.fromJson(msg.data, UserModel.class);
        }

    }

    @Override
    public PurchaseListModel loadPurchaseHistory(int id, boolean managerView) {
        msg.code = MessageModel.GET_PURCHASE_LIST;
        msg.data = Integer.toString(id);

        try {
            msg = adapter.exchange(msg, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return null;
        else {
            return gson.fromJson(msg.data, PurchaseListModel.class);
        }
    }

    @Override
    public int saveUser(UserModel user) { return 1; }
}