



import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.List;


public class gerenciarProcessos {


    public static void main(String[] args) {

        Scanner ler = new Scanner(System.in);

        System.out.println("O número de processos em execução");
        int numerodeP = ler.nextInt();


        List<Processo> processos = new ArrayList<>();

        for (int i = 1; i <= numerodeP; i++) {
            System.out.println("digite o pid do processo " + i + ":");
            int pid = ler.nextInt();


            System.out.println("tempo de execucao do processo" + i + ":");
            int tempo = ler.nextInt();

            Processo processo = new Processo(pid, tempo);
            processos.add(processo);
        }

        System.out.println("Escolha a política de escalonamento:");
        System.out.println("1. FCFS");
        System.out.println("2. SJF (Não Preemptivo)");
        System.out.println("3. Round Robin");
        int politica = ler.nextInt();

        int quantum = 0;
        int ttc = 0;
        if (politica == 3) {
            System.out.println("digite o quantum");
            quantum = ler.nextInt();

            System.out.println("ttc");
            ttc = ler.nextInt();

        }


        switch (politica) {
            case 1 -> fcfs(processos);
            case 2 -> sjfnao(processos);
            case 3 -> rr(processos, quantum, ttc);
            default -> System.out.println("inválida");
        }
        ler.close();
    }

    public static void fcfs(List<Processo> processos) {
        System.out.println("FCFS");

        int tempo = 0;
        int tempototal = 0;

        for (Processo processo : processos) {
            System.out.println("Executando processo " + processo.getPid() + ", tempo: " + tempo);

            tempototal += tempo;
            tempo += processo.getTempo();
        }

        double tempoMedioExecucao = (double) tempototal / processos.size();

        System.out.println("Tempo de execução total: " + tempototal);
        System.out.println("Tempo médio de execução: " + tempoMedioExecucao);
    }

    public static void sjfnao(List<Processo> processos) {
        System.out.println("SJF não preemptivo");

        processos.sort(Comparator.comparing(Processo::getTempo));

        int tempo = 0;
        int tempototal = 0;


        for (Processo processo : processos) {

            System.out.println("Executando " + processo.getPid() + ", tempo: " + tempo);
            tempo += processo.getTempo();
            tempototal += processo.getTempo();
        }


        double media = (double) tempototal / processos.size();
        System.out.println("Tempo de execução total: " + tempototal);
        System.out.println("Tempo médio de execução: " + media);
    }




    public static void rr(List<Processo> processos, int quantum, int ttc) {
        System.out.println("Round Robin");

        List<Processo> processosEmEspera = new ArrayList<>(processos);
        int tempoTotal = 0;
        int trocasDeContexto = 0;

        while (!processosEmEspera.isEmpty()) {
            Processo processoAtual = processosEmEspera.get(0);

            if (processoAtual.getTempo() <= quantum) {
                tempoTotal += processoAtual.getTempo();
                System.out.println("Executando processo " + processoAtual.getPid() + ", tempo: " + tempoTotal);
                processosEmEspera.remove(0);
            } else {
                tempoTotal += quantum;
                processoAtual.setTempo(processoAtual.getTempo() - quantum);
                System.out.println("Executando processo " + processoAtual.getPid() + ", tempo: " + tempoTotal);
                processosEmEspera.remove(0);
                processosEmEspera.add(processoAtual);
                trocasDeContexto++;
                tempoTotal += ttc;
            }


        }

        System.out.println("Tempo de execução total: " + tempoTotal);

    }
}







