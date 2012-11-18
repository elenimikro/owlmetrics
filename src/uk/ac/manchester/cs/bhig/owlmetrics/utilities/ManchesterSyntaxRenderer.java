package uk.ac.manchester.cs.bhig.owlmetrics.utilities;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxObjectRenderer;

public class ManchesterSyntaxRenderer implements OWLObjectRenderer {
	private ManchesterOWLSyntaxObjectRenderer ren;
	private WriterDelegate writerDelegate;

	public ManchesterSyntaxRenderer() {
		this.writerDelegate = new WriterDelegate();
		this.ren = new ManchesterOWLSyntaxObjectRenderer(this.writerDelegate,
				new SimpleShortFormProvider());
		this.ren.setUseWrapping(false);
	}

	public synchronized String render(OWLObject object) {
		this.writerDelegate.reset();
		object.accept(this.ren);
		return this.writerDelegate.toString();
	}

	public synchronized void setShortFormProvider(ShortFormProvider shortFormProvider) {
		this.ren = new ManchesterOWLSyntaxObjectRenderer(this.writerDelegate, shortFormProvider);
	}

	private static class WriterDelegate extends Writer {
		private StringWriter delegate;

		public WriterDelegate() {
			// TODO Auto-generated constructor stub
		}

		private void reset() {
			this.delegate = new StringWriter();
		}

		@Override
		public String toString() {
			return this.delegate.getBuffer().toString();
		}

		@Override
		public void close() throws IOException {
			this.delegate.close();
		}

		@Override
		public void flush() throws IOException {
			this.delegate.flush();
		}

		@Override
		public void write(char cbuf[], int off, int len) throws IOException {
			this.delegate.write(cbuf, off, len);
		}
	}
}
