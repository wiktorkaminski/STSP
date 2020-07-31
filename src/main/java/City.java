public class City {
    private int id;
    private String name;
    private float longitude;
    private float latitude;
    private static int counterId = 0;

    public City(float longitude, float latitude) {
        this.id = counterId;
        counterId++;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public City(String name, float longitude, float latitude) {
        this.id = counterId;
        counterId++;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
