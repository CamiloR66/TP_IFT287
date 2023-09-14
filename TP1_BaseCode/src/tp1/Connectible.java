package tp1;

public class Connectible
{
    ConnectibleType connectibleType;
    String name;
    int id;
    float volume;
    float startRadius;
    float endRadius;
    float length;
    boolean isVolume = false;
    boolean isEndRadius = false;
    boolean isStartRadius = false;
    
    public ConnectibleType getConnectibleType() {
		return connectibleType;
	}
	public void setConnectibleType(ConnectibleType connectibleType) {
		this.connectibleType = connectibleType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getVolume() {
		return volume;
	}
	public void setVolume(float volume) {
		this.volume = volume;
	}
	public float getStartRadius() {
		return startRadius;
	}
	public void setStartRadius(float startRadius) {
		this.startRadius = startRadius;
	}
	public float getEndRadius() {
		return endRadius;
	}
	public void setEndRadius(float endRadius) {
		this.endRadius = endRadius;
	}
	public float getLength() {
		return length;
	}
	public void setLength(float length) {
		this.length = length;
	}

    boolean isLength = false;
    
    public Connectible(String name, int id, ConnectibleType connectibleType) {
		this.id = id;
		this.name = name;
		this.connectibleType = connectibleType;
	}
	public boolean isLength() {
		return isLength;
	}
	public void setLength(boolean isLength) {
		this.isLength = isLength;
	}
	public boolean isVolume() {
		return isVolume;
	}
	public void setVolume(boolean isVolume) {
		this.isVolume = isVolume;
	}
	public boolean isEndRadius() {
		return isEndRadius;
	}
	public void setEndRadius(boolean isEndRadius) {
		this.isEndRadius = isEndRadius;
	}
	public boolean isStartRadius() {
		return isStartRadius;
	}
	public void setStartRadius(boolean isStartRadius) {
		this.isStartRadius = isStartRadius;
	}
	
    
}
