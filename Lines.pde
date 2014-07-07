class Lines{
  
  private float y, len;
  private boolean alive;
  
  Lines(float x, float y, int len){
    this.y = y;
    this.len = len;
    this.alive = true;
  }

  public float getY(){
    return this.y;
  }
  
  public float[] getPosition(){
    return new float[] {this.y};
  }

  public void setAlive(boolean alive){
    this.alive = alive;
  }

  public boolean getAlive(){
    return this.alive;
  }
  
  public void update(float y){
      this.y = y;
  }
   
  void draw(){
    line(0, this.y, len, this.y);
    
  }
  
}