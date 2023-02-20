import java.rmi.*;

public  class InfoClientImpl  implements  InfoClient{

	private Integer _id;
 
	public InfoClientImpl(Integer id) {
        _id=id;
	}

	public Integer getId() throws RemoteException {
		return _id;
	}
}
