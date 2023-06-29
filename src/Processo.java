class Processo {
    private int pid;
    private int tempo;

    public Processo(int pid, int tempo) {
        this.pid = pid;

        this.tempo = tempo;
    }

    public int getPid() {
        return pid;
    }



    public int getTempo() {
        return tempo;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }



    public void setTempo(int tempo) {
        this.tempo = tempo;
    }
    public String toString() {
        return "Processo [pid=" + pid +  ", tempo=" + tempo + "]";
    }
}