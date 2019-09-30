

import java.math.BigInteger;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import client.view.StatusWindow;
import network.Sniffer;
import network.SnifferCallback;

public class CodeBreaker implements SnifferCallback {

    private final JPanel workList;
    private final JPanel progressList;
    
    private final JProgressBar mainProgressBar;

    // ---------------------------------------------------------------------
    
    private CodeBreaker() {
        StatusWindow w  = new StatusWindow();

        workList        = w.getWorkList();
        progressList    = w.getProgressList();
        mainProgressBar = w.getProgressBar();
       
        
        new Sniffer(this).start();
        
    }
    
    // -----------------------------------------------------------------------
    
    public static void main(String[] args) throws Exception {

        /*
         * Most Swing operations (such as creating view elements) must be
         * performed in the Swing EDT (Event Dispatch Thread).
         * 
         * That's what SwingUtilities.invokeLater is for.
         */

        SwingUtilities.invokeLater(() -> new CodeBreaker());
       
    }

    // -----------------------------------------------------------------------

    /** Called by a Sniffer thread when an encrypted message is obtained. */
    @Override
    public void onMessageIntercepted(String message, BigInteger n) {
        System.out.println("message intercepted (N=" + n + ")...");
        WorklistItem wli = new WorklistItem(n, "N: " + n);
        Jbutton button = new Jbutton("GET CRACKING");
        ProgressTracker tracker = new tracker();
        button.addActionListener(e -> {
        	moveItem(wli, button);
        	startCracking(message, n, tracker);
        });
        wli.add(button);
        wli.(wli);
    }
    
    private void startCracking(String code, BigInterger n, ProgressTracker tracker) {
    	
    }
    
    private void moveItem(WorklistItem wli, JButton button) {
    	
    }
    
    // -----------------------------------------------------------------------

    /** ProgressTracker: reports how far factorization has progressed */ 
    private static class Tracker implements ProgressTracker {
        private int totalProgress = 0;
        private int prevPercent = -1;

        /**
         * Called by Factorizer to indicate progress. The total sum of
         * ppmDelta from all calls will add upp to 1000000 (one million).
         * 
         * @param  ppmDelta   portion of work done since last call,
         *                    measured in ppm (parts per million)
         */
        @Override
        public void onProgress(int ppmDelta) {
            totalProgress += ppmDelta;
            int percent = totalProgress / 10000;
            if (percent != prevPercent) {
                System.out.println(percent + "%");
                prevPercent = percent;
            }
        }
    }
}
