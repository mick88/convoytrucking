package com.mick88.convoytrucking.interfaces;



public interface Downloadable
{
	public enum DataStatus
	{
		Empty,
		Loading,
		Loaded,
	}
	
	boolean downloadData();
	boolean downloadData(OnDownloadListener downloadListener);
	
	void onDataDownloaded();
	void onDownloadStarted();
	void onDownloadCancelled();
	DataStatus getDataStatus();
}
