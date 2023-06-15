



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

            System.out.println("prioridade" + i + ":");
            int prioridade = ler.nextInt();

            System.out.println("tempo de execucao do processo" + i + ":");
            int tempo = ler.nextInt();

            Processo processo = new Processo(pid, prioridade, tempo);
            processos.add(processo);
        }

        System.out.println("Escolha a política de escalonamento:");
        System.out.println("1. FCFS");
        System.out.println("2. SJF (Não Preemptivo)");
        System.out.println("3. SJF (Preemptivo - SRTF)");
        System.out.println("4. Round Robin");
        int politica = ler.nextInt();

        int quantum = 0;
        int ttc = 0;
        if (politica == 4) {
            System.out.println("digite o quantum");
            quantum = ler.nextInt();

            System.out.println("ttc");
            ttc = ler.nextInt();

        }


        switch (politica) {
            case 1 -> fcfs(processos);
            case 2 -> sjfnao(processos);
            case 3 -> srtf(processos);
            case 4 -> rr(processos, quantum, ttc);
            default -> System.out.println("inválida");
        }
        ler.close();
    }

    public static void fcfs(List<Processo> processos) {
        System.out.println("FCFS");

        int tempo = 0;
        int tempototal = 0;

        for (Processo processo : processos) {
            tempo += processo.getTempo();
            System.out.println("Executando processo " + processo.getPid() + ", tempo: " + tempo);

            tempototal = tempo + tempototal;
        }
        double tempoMedioExecucao = (double) tempototal / processos.size();

        System.out.println("Tempo de execução total: " + tempototal);
        System.out.println("Tempo médio de execução: " + tempoMedioExecucao);
    }

    public static void sjfnao(List<Processo> processos) {
        System.out.println("SJF não preemptivo");

        processos.sort(Comparator.comparing(Processo::getTempo).thenComparing(Processo::getPrioridade));

        int tempo = 0;
        int tempototal = 0;


        for (Processo processo : processos) {
            tempo += processo.getTempo();
            System.out.println("Executando " + processo.getPid() + ", tempo: " + tempo);
            tempototal = tempo + tempototal;
        }


        double media = (double) tempototal / processos.size();
        System.out.println("Tempo de execução total: " + tempototal);
        System.out.println("Tempo médio de execução: " + media);
    }


    public static void srtf(List<Processo> processos) {
        System.out.println("SJF Preemptivo (SRTF)");

        List<Processo> processosRestantes = new ArrayList<>(processos);
        int tempoAtual = 0;
        int tempototal = 0;
        Processo processoAtual = null;

        while (!processosRestantes.isEmpty()) {
            Processo proximoProcesso = null;
            int menorTempo = Integer.MAX_VALUE;

            // Encontrar o próximo processo com menor tempo de execução restante
            for (Processo processo : processosRestantes) {
                if (processo.getTempo() < menorTempo && processo.getTempo() > 0) {
                    menorTempo = processo.getTempo();
                    proximoProcesso = processo;
                }
            }

            // Se houver um novo processo com menor tempo, interromper o processo atual
            if (processoAtual != null && proximoProcesso != null && proximoProcesso.getTempo() < processoAtual.getTempo()) {
                System.out.println("Interrompendo processo " + processoAtual.getPid() + ", tempo: " + tempototal);
                processoAtual = proximoProcesso;
            }

            if (processoAtual == null && proximoProcesso != null) {
                processoAtual = proximoProcesso;
            }

            if (processoAtual != null) {
                System.out.println("Executando processo " + processoAtual.getPid() + ", tempo: " + tempototal);
                processoAtual.setTempo(processoAtual.getTempo() - 1);
                tempototal++;
                tempoAtual++;

                if (processoAtual.getTempo() == 0) {
                    processosRestantes.remove(processoAtual);
                    processoAtual = null;
                }
            }
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
                trocasDeContexto++;
            }

            if (!processosEmEspera.isEmpty() && ttc > 0) {
                tempoTotal += ttc;
                System.out.println("Troca de contexto, tempo: " + tempoTotal);
            }

            processosEmEspera.add(processoAtual);
        }

        double mediaTempoExecucao = (double) tempoTotal / processos.size();

        System.out.println("Tempo de execução total: " + tempoTotal);
        System.out.println("Tempo médio de execução: " + mediaTempoExecucao);
    }
}






