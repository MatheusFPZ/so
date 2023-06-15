class Processo {
    private int pid;
    private int prioridade;
    private int tempo;

    public Processo(int pid, int prioridade, int tempo) {
        this.pid = pid;
        this.prioridade = prioridade;
        this.tempo = tempo;
    }

    public int getPid() {
        return pid;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public int getTempo() {
        return tempo;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }
    public String toString() {
        return "Processo [pid=" + pid + ", prioridade=" + prioridade + ", tempo=" + tempo + "]";
    }
}