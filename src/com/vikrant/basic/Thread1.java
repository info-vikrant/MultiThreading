package com.vikrant.basic;


//Objective: To know meaning of reentrant
public class Thread1 extends Thread{

	
		public static void main(String[] args) {

			P p = new P();
			T1 t1= new T1();
			t1.SetP(p);
			T2 t2= new T2();
			t2.SetP(p);
			t1.start();
			t2.start();
		System.out.println("Hello");
	}
}

class T1 extends Thread{
P p = null;
public void SetP(P p)
{
	this.p=p;}	
	
	@Override
	public void run() {
		try {
			p.m1();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
}

class T2 extends Thread{
P p = null;
public void SetP(P p)
{
	this.p=p;}	
	
	@Override
	public void run() {
		try {
			p.m2();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
}

class P
{
   synchronized public void m1() throws InterruptedException{
	   Thread.sleep(1000);

	   System.out.println("m1 method");
	   System.out.println(Thread.currentThread());
	   Thread.sleep(1000);
	   m2();
   } 	
   
   //Try making below method static
   synchronized  public void m2() throws InterruptedException{
	   Thread.sleep(1000);

	   System.out.println("m2 method");
	   System.out.println(Thread.currentThread());
	   Thread.sleep(1000);
   } 


}
