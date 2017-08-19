package com.vikrant.lock;

import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//Objective to use Re-entrant lock and make your own lock and re-entrant lock
public class Main {

	public static void main(String[] args) {
		PrinterQueue printerQueue = new PrinterQueue();
		Thread thread[] = new Thread[10];
		for (int i = 0; i < 10; i++) {
			thread[i] = new Thread(new PrintingJob(printerQueue), "Thread " + i);
		}
		for (int i = 0; i < 10; i++) {
			thread[i].start();
		}
	}
}

class PrinterQueue {
	//private final MyLock queueLock = new MyLock();
	private final Lock queueLock = new  ReentrantLock();

	public void printJob(Object document) throws InterruptedException {
		queueLock.lock();
		try {
			Long duration = (long) (Math.random() * 10000);
			System.out.println(Thread.currentThread().getName() + ": PrintQueue: Printing a Job during "
					+ (duration / 1000) + " seconds :: Time - " + new Date());
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.printf("%s: The document has been printed\n", Thread.currentThread().getName());
			queueLock.unlock();
			// If you don’t call the unlock() method at the end of the critical
			// section, the other threads that are waiting for that block will
			// be waiting forever, causing a deadlock situation.
		}
	}
	
	
/*synchronized public void printJob(Object document) {
		//queueLock.lock();
		try {
			Long duration = (long) (Math.random() * 10000);
			System.out.println(Thread.currentThread().getName() + ": PrintQueue: Printing a Job during "
					+ (duration / 1000) + " seconds :: Time - " + new Date());
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.printf("%s: The document has been printed\n", Thread.currentThread().getName());
			//queueLock.unlock();
		}
	}*/
}

class PrintingJob implements Runnable {
	private PrinterQueue printerQueue;

	public PrintingJob(PrinterQueue printerQueue) {
		this.printerQueue = printerQueue;
	}

	@Override
	public void run() {
		System.out.printf("%s: Going to print a document\n", Thread.currentThread().getName());
		try {
			printerQueue.printJob(new Object());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

//This is how lock class look like 
//Imp , this is normal lock not a re-entrant lock 
//To understand re-entract-check basic package
 class MyLock{

	  private boolean isLocked = false;

	  public synchronized void lock()
	  throws InterruptedException{
	    while(isLocked){
	      wait();
	    }
	    isLocked = true;
	  }

	  public synchronized void unlock(){
	    isLocked = false;
	    notify();
	  }
	}
 
 
 class MyLockWithReentrant {
	 boolean isLocked = false;
	 Thread lockedBy = null;
	 int lockedCount = 0;

	 public synchronized void lock() throws InterruptedException {
		 Thread callingThread = Thread.currentThread();
		 while (isLocked && lockedBy != callingThread) {
			 wait();
		 }
		 isLocked = true;
		 lockedCount++;
		 lockedBy = callingThread;
	 }

	 public synchronized void unlock() {
		 if (Thread.currentThread() == this.lockedBy) {
			 lockedCount--;

			 if (lockedCount == 0) {
				 isLocked = false;
				 notify();
			 }
		 }
	 }
}