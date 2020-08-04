package commonadapter.interfaces;



public interface Entity {

    Float getSpeedX();
    void setSpeedX(Float speedX);

    Float getSpeedY();
    void setSpeedY(Float speedY);

    Float getAccelerationX();
    void setAccelerationX(Float accelerationX);

    Float getAccelerationY();
    void setAccelerationY(Float accelerationY);

    Float getLength();
    void setLength(Float length);

    Float getWidth();
    void setWidth(Float width);

    Float getDistance();
    void setDistance(Float distance);

    //  0 -> "our" lane
    // -1 -> first lane on the left
    // -2 -> second lane on the left
    //  1 -> first lane on the right
    //  etc.
    Integer getLane();
    void setLane(Integer lane);
}