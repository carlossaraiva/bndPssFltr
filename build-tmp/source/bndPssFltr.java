import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.ugens.*; 
import ddf.minim.effects.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class bndPssFltr extends PApplet {






Minim minim;
AudioPlayer player;
BandPass bpf;

float r = 0, r2 = 0;
float a = 255;

int size = 5;
Lines[] reta = new Lines[size];
int pSize;
int aleatorio;
float lineWidth;
float vol = 0.25f, fil = 0, resonance = 0;

public void setup()
{
  size(1280, 720);
  background(0);
  rectMode(CENTER);

  minim = new Minim(this);
  player = minim.loadFile("bass.wav");
  player.loop();
  bpf = new BandPass(440, 20, player.sampleRate());
  player.addEffect(bpf);
  
  for(int i = 0; i < reta.length; i++){
    reta[i] = new Lines(0, 0, width);
  } 
   
}

public void draw()
{
  //background(0)
  crossHair();

  float speed = dist(pmouseX, pmouseY, mouseX, mouseY);
  float alpha = map(speed, 0, 20, 0, 10);
  lineWidth = map(speed, 0, 10, 1, 3);
  lineWidth = constrain(lineWidth, 0, 4);

  if(mousePressed)
  {      
    drawLines();
    fill(0);
  float red = map(mouseX, 0, width, 0, 255);
  float blue = map(mouseY, 0, width, 0, 255);
  float green = dist(mouseX,mouseY,width/2,height/2);

  stroke(red, green, blue, 255);
  strokeWeight(lineWidth);

    line(pmouseX, pmouseY,mouseX, mouseY);

    pushMatrix();
    strokeWeight(lineWidth);
    translate(mouseX, mouseY);
    //rotate(r2);
    stroke(red, green, blue, 255);
    popMatrix();
    r2 += 0.1f; 
   
    float passBand = map(mouseY, 0, height, 2000, 100);
    bpf.setFreq(passBand);
    float bandWidth = map(mouseX, 0, width, 500, 50);
    bpf.setBandWidth(bandWidth);
    // prints the new values of the coefficients in the console
    bpf.printCoeff();

    noFill();
    ellipse(mouseX, mouseY, 100, 100);
    
  }
  else 
  {
    println("Aqui"); 
  }
       
  fadescr(0, 0, 0);
}


public void fadescr(int r, int g, int b) 
{ 
  int red, green, blue;
  loadPixels();
  for (int i = 0; i < pixels.length; i++) {
    red = (pixels[i] >> 16) & 0x000000ff; 
    green = (pixels[i] >> 8) & 0x000000ff; 
    blue = pixels[i] & 0x000000ff; 
    pixels[i] = (((red+((r-red)>>3)) << 16) | ((green+((g-green)>>3)) << 8) | (blue+((b-blue)>>3)));
  }
  updatePixels();
}

public void crossHair(){
  stroke(255, 255, 255);
  strokeWeight(random(0, 6));
  
  if(mousePressed){
    stroke(255, 0, 0);
  }

  line(0, mouseY, width, mouseY);
  strokeWeight(random(0, 6));
  stroke(255, 255, 255);
  
  if(mousePressed)
  {
    stroke(255, 0, 0);
  }
  line(mouseX, 0, mouseX, height);
}

public void drawLines()
{
  
  stroke(255, 255, 255);
  
  for(int i = 0; i < size; i++)    
  {
    reta[i].update(random(height));
  } 

  stroke(255, 255, 255);

  for(int i = 0; i < size -1; i++)
  {
    reta[i].draw();
  }   
}

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
   
  public void draw(){
    line(0, this.y, len, this.y);
    
  }
  
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "bndPssFltr" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
