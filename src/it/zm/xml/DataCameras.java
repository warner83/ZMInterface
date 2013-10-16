package it.zm.xml;

public class DataCameras extends DataManagement {

	public DataCameras(String baseUrl) {
		super(baseUrl);
	}

	@Override
	protected String getOperationSuffix() {
		return "?skin=xml";
	}

}
