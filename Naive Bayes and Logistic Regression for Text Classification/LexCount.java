class LexCount {

    long hamOccurance_count,spamOccurance_count;
    

    LexCount() {
        hamOccurance_count = 1;spamOccurance_count = 1;
        
    }

    public LexCount(long hamOccurance_count, long numberOfOccurencesInSpamDocument) {
        this.hamOccurance_count = hamOccurance_count;
        this.spamOccurance_count = numberOfOccurencesInSpamDocument;
    }
}